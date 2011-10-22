/*******************************************************************************
 * Copyright 2011 Andre Rouel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
grammar LogFormat;

options {
  language = Java;
}

@header {
  package net.sf.jacclog.logformat;
  import net.sf.jacclog.logformat.LogFormat;
}

@lexer::header {
  package net.sf.jacclog.logformat;
}

@members {

  private LogFormat.Builder builder = new LogFormat.Builder();
  
  public LogFormat.Builder getBuilder() {
    return builder;
  }

  private String lastError = "";

  public String getLastError() {
    return lastError;
  }

  List<RecognitionException> exceptions = new ArrayList<RecognitionException>();

  public List<RecognitionException> getExceptions() {
    return exceptions;
  }

  @Override
  public void reportError(RecognitionException e) {
    super.reportError(e);
    exceptions.add(e);
  }

}

@rulecatch {

  catch(RecognitionException re) {
    lastError = this.getErrorMessage(re, this.getTokenNames());
    System.out.println("Catch=" + this.getLastError());
    throw re;
  }
  
}



/****************
 * Parser rules *
 ****************/
format returns [LogFormat l]
  :
    ( ( WHITESPACE | SEPARATOR )* field ( WHITESPACE | SEPARATOR )* )* EOF  { $l = this.getBuilder().build(); }
  ;

field : ( basic_field | complex_field ); 

basic_field
  : ( remote_ip_address
    | local_ip_address
    | response_in_bytes
    | response_in_bytes_clf
    | request_in_millis
    | filename
    | remote_host
    | request_protocol
    | remote_logname
    | request_method
    | server_port
    | process_id
    | query_string
    | request_first_line
    | status_code
    | last_status_code
    | request_time
    | request_in_seconds
    | remote_user
    | url_path
    | servername_canonical
    | servername
    | connection_status
    | bytes_received
    | bytes_send
    | ignore
    )
  ;

  /**
   * %...a Remote IP-address
   */
  remote_ip_address : PLACEHOLDER 'a'
    {
      this.getBuilder().appendRemoteIpAddressField();
    };

  /**
   * %...A Local IP-address
   */
  local_ip_address : PLACEHOLDER 'A'
    {
      this.getBuilder().appendLocalIpAddressField();
    };

  /**
   * %...B Size of response in bytes, excluding HTTP headers.
   */
  response_in_bytes : PLACEHOLDER 'B'
    {
      this.getBuilder().appendResponseInBytesField();
    };

  /**
   * %...b Size of response in bytes, excluding HTTP headers. In CLF format, i.e. a '-' rather than a 0 when no bytes
   * are sent.
   */
  response_in_bytes_clf : PLACEHOLDER 'b'
    {
      this.getBuilder().appendResponseInBytesClfField();
    };

  /**
   * %...D The time taken to serve the request, in microseconds.
   */
  request_in_millis : PLACEHOLDER 'D'
    {
      this.getBuilder().appendRequestInMillisField();
    };

  /**
   * %...f Filename
   */
  filename : PLACEHOLDER 'f'
    {
      this.getBuilder().appendFilenameField();
    };

  /**
   * %...h Remote host
   */
  remote_host : PLACEHOLDER 'h'
    {
      this.getBuilder().appendRemoteHostField();
    };

  /**
   * %...H The request protocol
   */
  request_protocol : PLACEHOLDER 'H'
    {
      this.getBuilder().appendRequestProtocolField();
    };

  /**
   * %...l Remote logname (from identd, if supplied). This will return a dash unless IdentityCheck is set On.
   */
  remote_logname : PLACEHOLDER 'l'
    {
      this.getBuilder().appendRemoteLognameField();
    };

  /**
   * %...m The request method
   */
  request_method : PLACEHOLDER 'm'
    {
      this.getBuilder().appendRequestMethodField();
    };

  /**
   * %...p The canonical port of the server serving the request
   */
  server_port : PLACEHOLDER 'p'
    {
      this.getBuilder().appendServerPortField();
    };

  /**
   * %...P The process ID of the child that serviced the request.
   */
  process_id : PLACEHOLDER 'P'
    {
      this.getBuilder().appendProcessIdField();
    };

  /**
   * %...q The query string (prepended with a ? if a query string exists, otherwise an empty string)
   */
  query_string : PLACEHOLDER 'q'
    {
      this.getBuilder().appendQueryStringField();
    };

  /**
   * %...r First line of request
   */
  request_first_line : PLACEHOLDER 'r'
    {
      this.getBuilder().appendRequestFirstLineField();
    };

  /**
   * %...s Status. For requests that got internally redirected, this is the status of the *original* request ---
   * %...>s for the last.
   */
  status_code : PLACEHOLDER 's'
    {
      this.getBuilder().appendHttpStatusField();
    };

  /**
   * %...s Status for the last request
   */
  last_status_code : PLACEHOLDER '>s'
    {
      this.getBuilder().appendHttpLastStatusField();
    };

  /**
   * %...t Time the request was received (standard english format)
   */
  request_time : PLACEHOLDER 't'
    {
      this.getBuilder().appendRequestTimeField();
    };

  /**
   * %...T The time taken to serve the request, in seconds.
   */
  request_in_seconds : PLACEHOLDER 'T'
    {
      this.getBuilder().appendRequestInSecondsField();
    };

  /**
   * %...u Remote user (from auth; may be bogus if return status (%s) is 401)
   */
  remote_user : PLACEHOLDER 'u'
    {
      this.getBuilder().appendRemoteUserField();
    };

  /**
   * %...U The URL path requested, not including any query string.
   */
  url_path : PLACEHOLDER 'U'
    {
      this.getBuilder().appendUrlPathField();
    };

  /**
   * %...v The canonical ServerName of the server serving the request.
   */
  servername_canonical : PLACEHOLDER 'v'
    {
      this.getBuilder().appendCanonicalServerNameField();
    };

  /**
   * %...V The server name according to the UseCanonicalName setting.
   */
  servername : PLACEHOLDER 'V'
    {
      this.getBuilder().appendServerNameField();
    };

  /**
   * %...X Connection status when response is completed:
   * <ul>
   * <li>X = connection aborted before the response completed.</li>
   * <li>+ = connection may be kept alive after the response is sent.</li>
   * <li>- = connection will be closed after the response is sent.</li>
   * </ul>
   */
  connection_status : PLACEHOLDER 'X'
    {
      this.getBuilder().appendConnectionStatusField();
    };

  /**
   * %...I Bytes received, including request and headers, cannot be zero. You need to enable mod_logio to use this.
   */
  bytes_received : PLACEHOLDER 'I'
    {
      this.getBuilder().appendBytesReceivedField();
    };

  /**
   * %...O Bytes sent, including headers, cannot be zero. You need to enable mod_logio to use this.
   */
  bytes_send : PLACEHOLDER 'O'
    {
      this.getBuilder().appendBytesSendField();
    };

  /**
   * A common slot for ignorable fields, can be applied multiple times
   */
  ignore : PLACEHOLDER '0'
    {
      this.getBuilder().appendIgnorableField();
    };




complex_field
  : ( request_header_line
    | response_header_line
    | pid_or_tid
    | request_time_custom
    )
  ;

  /**
   * %...{Foobar}i The contents of Foobar: header line(s) in the request sent to the server. Changes made by other
   * modules (e.g. mod_headers) affect this.
   */
  request_header_line
    : PLACEHOLDER LPAREN request_header_fields RPAREN 'i'
    ;
  request_header_fields
    : request_accept
    | request_accept_charset
    | request_accept_encoding
    | request_accept_language
    | request_authorization
    | request_cache_control
    | request_connection
    | request_cookie
    | request_content_length
    | request_content_type
    | request_date
    | request_expect
    | request_from
    | request_host
    | request_if_match
    | request_if_modified_since
    | request_if_none_match
    | request_if_range
    | request_if_unmodified_since
    | request_max_forwards
    | request_pragma
    | request_proxy_authorization
    | request_range
    | request_referer
    | request_te
    | request_upgrade
    | request_user_agent
    | request_via
    | request_warning
    ;
  request_accept : 'Accept'
    { this.getBuilder().appendRequestHeaderAcceptField(); }
    ;
  request_accept_charset : 'Accept-Charset'
    { this.getBuilder().appendRequestHeaderAcceptCharsetField(); }
    ;
  request_accept_encoding : 'Accept-Encoding'
    { this.getBuilder().appendRequestHeaderAcceptEncodingField(); }
    ;
  request_accept_language : 'Accept-Language'
    { this.getBuilder().appendRequestHeaderAcceptLanguageField(); }
    ;
  request_authorization : 'Authorization'
    { this.getBuilder().appendRequestHeaderAuthorizationField(); }
    ;
  request_cache_control : 'Cache-Control'
    { this.getBuilder().appendRequestHeaderCacheControlField(); }
    ;
  request_connection : 'Connection'
    { this.getBuilder().appendRequestHeaderConnectionField(); }
    ;
  request_cookie : 'Cookie'
    { this.getBuilder().appendRequestHeaderCookieField(); }
    ;
  request_content_length : 'Content-Length'
    { this.getBuilder().appendRequestHeaderContentLengthField(); }
    ;
  request_content_type : 'Content-Type'
    { this.getBuilder().appendRequestHeaderContentTypeField(); }
    ;
  request_date : 'Date'
    { this.getBuilder().appendRequestHeaderDateField(); }
    ;
  request_expect : 'Expect'
    { this.getBuilder().appendRequestHeaderExpectField(); }
    ;
  request_from : 'From'
    { this.getBuilder().appendRequestHeaderFromField(); }
    ;
  request_host : 'Host'
    { this.getBuilder().appendRequestHeaderHostField(); }
    ;
  request_if_match : 'If-Match'
    { this.getBuilder().appendRequestHeaderIfMatchField(); }
    ;
  request_if_modified_since : 'If-Modified-Since'
    { this.getBuilder().appendRequestHeaderIfModifiedSinceField(); }
    ;
  request_if_none_match : 'If-None-Match'
    { this.getBuilder().appendRequestHeaderIfNoneMatchField(); }
    ;
  request_if_range : 'If-Range'
    { this.getBuilder().appendRequestHeaderIfRangeField(); }
    ;
  request_if_unmodified_since : 'If-Unmodified-Since'
    { this.getBuilder().appendRequestHeaderIfUnmodifiedSinceField(); }
    ;
  request_max_forwards : 'Max-Forwards'
    { this.getBuilder().appendRequestHeaderMaxForwardsField(); }
    ;
  request_pragma : 'Pragma'
    { this.getBuilder().appendRequestHeaderPragmaField(); }
    ;
  request_proxy_authorization : 'Proxy-Authorization'
    { this.getBuilder().appendRequestHeaderProxyAuthorizationField(); }
    ;
  request_range : 'Range'
    { this.getBuilder().appendRequestHeaderRangeField(); }
    ;
  request_referer : 'Referer'
    { this.getBuilder().appendRequestHeaderRefererField(); }
    ;
  request_te : 'TE'
    { this.getBuilder().appendRequestHeaderTeField(); }
    ;
  request_upgrade : 'Upgrade'
    { this.getBuilder().appendRequestHeaderUpgradeField(); }
    ;
  request_user_agent : 'User-Agent'
    { this.getBuilder().appendRequestHeaderUserAgentField(); }
    ;
  request_via : 'Via'
    { this.getBuilder().appendRequestHeaderViaField(); }
    ;
  request_warning : 'Warning'
    { this.getBuilder().appendRequestHeaderWarningField(); }
    ;



  /**
   * %...{Foobar}o The contents of Foobar: header line(s) in the reply.
   */
  response_header_line
    : PLACEHOLDER LPAREN response_header_fields RPAREN 'o'
    ;
  response_header_fields
    : response_accept_ranges
    | response_age
    | response_allow
    | response_cache_control
    | response_connection
    | response_content_encoding
    | response_content_language
    | response_content_length
    | response_content_location
    | response_content_disposition
    | response_content_range
    | response_content_type
    | response_date
    | response_etag
    | response_expires
    | response_last_modified
    | response_link
    | response_location
    | response_p3p
    | response_pragma
    | response_proxy_authenticate
    | response_refresh
    | response_retry_after
    | response_server
    | response_set_cookie
    | response_strict_transport_security
    | response_trailer
    | response_transfer_encoding
    | response_vary
    | response_via
    | response_warning
    | response_www_authenticate
    ;
  response_accept_ranges : 'Accept-Ranges'
    { this.getBuilder().appendResponseHeaderAcceptRangesField(); }
    ;
  response_age : 'Age'
    { this.getBuilder().appendResponseHeaderAgeField(); }
    ;
  response_allow : 'Allow'
    { this.getBuilder().appendResponseHeaderAllowField(); }
    ;
  response_cache_control : 'Cache-Control'
    { this.getBuilder().appendResponseHeaderCacheControlField(); }
    ;
  response_connection : 'Connection'
    { this.getBuilder().appendResponseHeaderConnectionField(); }
    ;
  response_content_encoding : 'Content-Encoding'
    { this.getBuilder().appendResponseHeaderContentEncodingField(); }
    ;
  response_content_language : 'Content-Language'
    { this.getBuilder().appendResponseHeaderContentLanguageField(); }
    ;
  response_content_length : 'Content-Length'
    { this.getBuilder().appendResponseHeaderContentLengthField(); }
    ;
  response_content_location : 'Content-Location'
    { this.getBuilder().appendResponseHeaderContentLocationField(); }
    ;
  response_content_disposition : 'Content-Disposition'
    { this.getBuilder().appendResponseHeaderContentDispositionField(); }
    ;
  response_content_range : 'Content-Range'
    { this.getBuilder().appendResponseHeaderContentRangeField(); }
    ;
  response_content_type : 'Content-Type'
    { this.getBuilder().appendResponseHeaderContentTypeField(); }
    ;
  response_date : 'Date'
    { this.getBuilder().appendResponseHeaderDateField(); }
    ;
  response_etag : 'ETag'
    { this.getBuilder().appendResponseHeaderEtagField(); }
    ;
  response_expires : 'Expires'
    { this.getBuilder().appendResponseHeaderExpiresField(); }
    ;
  response_last_modified : 'Last-Modified'
    { this.getBuilder().appendResponseHeaderLastModifiedField(); }
    ;
  response_link : 'Link'
    { this.getBuilder().appendResponseHeaderLinkField(); }
    ;
  response_location : 'Location'
    { this.getBuilder().appendResponseHeaderLocationField(); }
    ;
  response_p3p : 'P3P'
    { this.getBuilder().appendResponseHeaderP3pField(); }
    ;
  response_pragma : 'Pragma'
    { this.getBuilder().appendResponseHeaderPragmaField(); }
    ;
  response_proxy_authenticate : 'Proxy-Authenticate'
    { this.getBuilder().appendResponseHeaderProxyAuthenticateField(); }
    ;
  response_refresh : 'Refresh'
    { this.getBuilder().appendResponseHeaderRefreshField(); }
    ;
  response_retry_after : 'Retry-After'
    { this.getBuilder().appendResponseHeaderRetryAfterField(); }
    ;
  response_server : 'Server'
    { this.getBuilder().appendResponseHeaderServerField(); }
    ;
  response_set_cookie : 'Set-Cookie'
    { this.getBuilder().appendResponseHeaderSetCookieField(); }
    ;
  response_strict_transport_security : 'Strict-Transport-Security'
    { this.getBuilder().appendResponseHeaderStrictTransportSecurityField(); }
    ;
  response_trailer : 'Trailer'
    { this.getBuilder().appendResponseHeaderTrailerField(); }
    ;
  response_transfer_encoding : 'Transfer-Encoding'
    { this.getBuilder().appendResponseHeaderTransferEncodingField(); }
    ;
  response_vary : 'Vary'
    { this.getBuilder().appendResponseHeaderVaryField(); }
    ;
  response_via : 'Via'
    { this.getBuilder().appendResponseHeaderViaField(); }
    ;
  response_warning : 'Warning'
    { this.getBuilder().appendResponseHeaderWarningField(); }
    ;
  response_www_authenticate : 'WWW-Authenticate'
    { this.getBuilder().appendResponseHeaderWwwAuthenticateField(); }
    ;



  /**
   * %...{format}P The process ID or thread id of the child that serviced the request. Valid formats are pid and tid.
   * (Apache 2.0.46 and later)
   */
  pid_or_tid
    : ( PLACEHOLDER LPAREN INTEGER RPAREN 'P' );

  /**
   * %...{format}t The time, in the form given by format, which should be in strftime(3) format. (potentially
   * localized)
   */
  request_time_custom
    : ( PLACEHOLDER LPAREN INTEGER RPAREN 't' );

  /**
   * %...{Foobar}C The contents of cookie Foobar in the request sent to the server.
   */
  cookie
    : ( PLACEHOLDER LPAREN ( alphanum | '_' ) RPAREN 'C' );

  /**
   * %...{FOOBAR}e The contents of the environment variable FOOBAR
   */
  env_var
    : ( PLACEHOLDER LPAREN alphanum RPAREN 'e' );






alphanum : INTEGER | LETTER;




/***************
 * Lexer rules *
 ***************/

fragment DIGIT : '0'..'9';
fragment LETTER : 'a'..'z'|'A'..'Z' ;
fragment SPECIAL : '-' | '_';

PLACEHOLDER : '%';
LPAREN : '{';
RPAREN : '}';

WHITESPACE : ( ' ' | '\t' | '\r' | '\n' );

SEPARATOR : ( '"' | '\'' | '[' | ']' );

INTEGER : DIGIT+ ;
IDENT : LETTER(LETTER | DIGIT | SPECIAL)* ;

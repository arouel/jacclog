/*******************************************************************************
 * Copyright 2011 André Rouél
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
package net.sf.jacclog.geoip.util;

public final class TimeZone {

	public static String determine(final String country) {
		return determine(country, "");
	}

	public static String determine(final String countryCode, final String regionCode) {
		if (countryCode == null) {
			throw new IllegalArgumentException("Argument 'countryCode' can not be null.");
		}

		if (countryCode.length() != 2) {
			throw new IllegalArgumentException("Argument 'countryCode' must be two characters long.");
		}

		if (regionCode == null) {
			throw new IllegalArgumentException("Argument 'regionCode' can not be null.");
		}

		String timezone = null;
		if (countryCode.equals("US")) {
			if (regionCode.equals("AL")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("AK")) {
				timezone = "America/Anchorage";
			} else if (regionCode.equals("AZ")) {
				timezone = "America/Phoenix";
			} else if (regionCode.equals("AR")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("CA")) {
				timezone = "America/Los_Angeles";
			} else if (regionCode.equals("CO")) {
				timezone = "America/Denver";
			} else if (regionCode.equals("CT")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("DE")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("DC")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("FL")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("GA")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("HI")) {
				timezone = "Pacific/Honolulu";
			} else if (regionCode.equals("ID")) {
				timezone = "America/Denver";
			} else if (regionCode.equals("IL")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("IN")) {
				timezone = "America/Indianapolis";
			} else if (regionCode.equals("IA")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("KS")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("KY")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("LA")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("ME")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("MD")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("MA")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("MI")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("MN")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("MS")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("MO")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("MT")) {
				timezone = "America/Denver";
			} else if (regionCode.equals("NE")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("NV")) {
				timezone = "America/Los_Angeles";
			} else if (regionCode.equals("NH")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("NJ")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("NM")) {
				timezone = "America/Denver";
			} else if (regionCode.equals("NY")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("NC")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("ND")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("OH")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("OK")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("OR")) {
				timezone = "America/Los_Angeles";
			} else if (regionCode.equals("PA")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("RI")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("SC")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("SD")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("TN")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("TX")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("UT")) {
				timezone = "America/Denver";
			} else if (regionCode.equals("VT")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("VA")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("WA")) {
				timezone = "America/Los_Angeles";
			} else if (regionCode.equals("WV")) {
				timezone = "America/New_York";
			} else if (regionCode.equals("WI")) {
				timezone = "America/Chicago";
			} else if (regionCode.equals("WY")) {
				timezone = "America/Denver";
			}
		} else if (countryCode.equals("CA")) {
			if (regionCode.equals("AB")) {
				timezone = "America/Edmonton";
			} else if (regionCode.equals("BC")) {
				timezone = "America/Vancouver";
			} else if (regionCode.equals("MB")) {
				timezone = "America/Winnipeg";
			} else if (regionCode.equals("NB")) {
				timezone = "America/Halifax";
			} else if (regionCode.equals("NL")) {
				timezone = "America/St_Johns";
			} else if (regionCode.equals("NT")) {
				timezone = "America/Yellowknife";
			} else if (regionCode.equals("NS")) {
				timezone = "America/Halifax";
			} else if (regionCode.equals("NU")) {
				timezone = "America/Rankin_Inlet";
			} else if (regionCode.equals("ON")) {
				timezone = "America/Rainy_River";
			} else if (regionCode.equals("PE")) {
				timezone = "America/Halifax";
			} else if (regionCode.equals("QC")) {
				timezone = "America/Montreal";
			} else if (regionCode.equals("SK")) {
				timezone = "America/Regina";
			} else if (regionCode.equals("YT")) {
				timezone = "America/Whitehorse";
			}
		} else if (countryCode.equals("AU")) {
			if (regionCode.equals("01")) {
				timezone = "Australia/Canberra";
			} else if (regionCode.equals("02")) {
				timezone = "Australia/NSW";
			} else if (regionCode.equals("03")) {
				timezone = "Australia/North";
			} else if (regionCode.equals("04")) {
				timezone = "Australia/Queensland";
			} else if (regionCode.equals("05")) {
				timezone = "Australia/South";
			} else if (regionCode.equals("06")) {
				timezone = "Australia/Tasmania";
			} else if (regionCode.equals("07")) {
				timezone = "Australia/Victoria";
			} else if (regionCode.equals("08")) {
				timezone = "Australia/West";
			}
		} else if (countryCode.equals("AS")) {
			timezone = "US/Samoa";
		} else if (countryCode.equals("CI")) {
			timezone = "Africa/Abidjan";
		} else if (countryCode.equals("GH")) {
			timezone = "Africa/Accra";
		} else if (countryCode.equals("DZ")) {
			timezone = "Africa/Algiers";
		} else if (countryCode.equals("ER")) {
			timezone = "Africa/Asmera";
		} else if (countryCode.equals("ML")) {
			timezone = "Africa/Bamako";
		} else if (countryCode.equals("CF")) {
			timezone = "Africa/Bangui";
		} else if (countryCode.equals("GM")) {
			timezone = "Africa/Banjul";
		} else if (countryCode.equals("GW")) {
			timezone = "Africa/Bissau";
		} else if (countryCode.equals("CG")) {
			timezone = "Africa/Brazzaville";
		} else if (countryCode.equals("BI")) {
			timezone = "Africa/Bujumbura";
		} else if (countryCode.equals("EG")) {
			timezone = "Africa/Cairo";
		} else if (countryCode.equals("MA")) {
			timezone = "Africa/Casablanca";
		} else if (countryCode.equals("GN")) {
			timezone = "Africa/Conakry";
		} else if (countryCode.equals("SN")) {
			timezone = "Africa/Dakar";
		} else if (countryCode.equals("DJ")) {
			timezone = "Africa/Djibouti";
		} else if (countryCode.equals("SL")) {
			timezone = "Africa/Freetown";
		} else if (countryCode.equals("BW")) {
			timezone = "Africa/Gaborone";
		} else if (countryCode.equals("ZW")) {
			timezone = "Africa/Harare";
		} else if (countryCode.equals("ZA")) {
			timezone = "Africa/Johannesburg";
		} else if (countryCode.equals("UG")) {
			timezone = "Africa/Kampala";
		} else if (countryCode.equals("SD")) {
			timezone = "Africa/Khartoum";
		} else if (countryCode.equals("RW")) {
			timezone = "Africa/Kigali";
		} else if (countryCode.equals("NG")) {
			timezone = "Africa/Lagos";
		} else if (countryCode.equals("GA")) {
			timezone = "Africa/Libreville";
		} else if (countryCode.equals("TG")) {
			timezone = "Africa/Lome";
		} else if (countryCode.equals("AO")) {
			timezone = "Africa/Luanda";
		} else if (countryCode.equals("ZM")) {
			timezone = "Africa/Lusaka";
		} else if (countryCode.equals("GQ")) {
			timezone = "Africa/Malabo";
		} else if (countryCode.equals("MZ")) {
			timezone = "Africa/Maputo";
		} else if (countryCode.equals("LS")) {
			timezone = "Africa/Maseru";
		} else if (countryCode.equals("SZ")) {
			timezone = "Africa/Mbabane";
		} else if (countryCode.equals("SO")) {
			timezone = "Africa/Mogadishu";
		} else if (countryCode.equals("LR")) {
			timezone = "Africa/Monrovia";
		} else if (countryCode.equals("KE")) {
			timezone = "Africa/Nairobi";
		} else if (countryCode.equals("TD")) {
			timezone = "Africa/Ndjamena";
		} else if (countryCode.equals("NE")) {
			timezone = "Africa/Niamey";
		} else if (countryCode.equals("MR")) {
			timezone = "Africa/Nouakchott";
		} else if (countryCode.equals("BF")) {
			timezone = "Africa/Ouagadougou";
		} else if (countryCode.equals("ST")) {
			timezone = "Africa/Sao_Tome";
		} else if (countryCode.equals("LY")) {
			timezone = "Africa/Tripoli";
		} else if (countryCode.equals("TN")) {
			timezone = "Africa/Tunis";
		} else if (countryCode.equals("AI")) {
			timezone = "America/Anguilla";
		} else if (countryCode.equals("AG")) {
			timezone = "America/Antigua";
		} else if (countryCode.equals("AW")) {
			timezone = "America/Aruba";
		} else if (countryCode.equals("BB")) {
			timezone = "America/Barbados";
		} else if (countryCode.equals("BZ")) {
			timezone = "America/Belize";
		} else if (countryCode.equals("CO")) {
			timezone = "America/Bogota";
		} else if (countryCode.equals("VE")) {
			timezone = "America/Caracas";
		} else if (countryCode.equals("KY")) {
			timezone = "America/Cayman";
		} else if (countryCode.equals("CR")) {
			timezone = "America/Costa_Rica";
		} else if (countryCode.equals("DM")) {
			timezone = "America/Dominica";
		} else if (countryCode.equals("SV")) {
			timezone = "America/El_Salvador";
		} else if (countryCode.equals("GD")) {
			timezone = "America/Grenada";
		} else if (countryCode.equals("FR")) {
			timezone = "Europe/Paris";
		} else if (countryCode.equals("GP")) {
			timezone = "America/Guadeloupe";
		} else if (countryCode.equals("GT")) {
			timezone = "America/Guatemala";
		} else if (countryCode.equals("GY")) {
			timezone = "America/Guyana";
		} else if (countryCode.equals("CU")) {
			timezone = "America/Havana";
		} else if (countryCode.equals("JM")) {
			timezone = "America/Jamaica";
		} else if (countryCode.equals("BO")) {
			timezone = "America/La_Paz";
		} else if (countryCode.equals("PE")) {
			timezone = "America/Lima";
		} else if (countryCode.equals("NI")) {
			timezone = "America/Managua";
		} else if (countryCode.equals("MQ")) {
			timezone = "America/Martinique";
		} else if (countryCode.equals("UY")) {
			timezone = "America/Montevideo";
		} else if (countryCode.equals("MS")) {
			timezone = "America/Montserrat";
		} else if (countryCode.equals("BS")) {
			timezone = "America/Nassau";
		} else if (countryCode.equals("PA")) {
			timezone = "America/Panama";
		} else if (countryCode.equals("SR")) {
			timezone = "America/Paramaribo";
		} else if (countryCode.equals("PR")) {
			timezone = "America/Puerto_Rico";
		} else if (countryCode.equals("KN")) {
			timezone = "America/St_Kitts";
		} else if (countryCode.equals("LC")) {
			timezone = "America/St_Lucia";
		} else if (countryCode.equals("VC")) {
			timezone = "America/St_Vincent";
		} else if (countryCode.equals("HN")) {
			timezone = "America/Tegucigalpa";
		} else if (countryCode.equals("YE")) {
			timezone = "Asia/Aden";
		} else if (countryCode.equals("JO")) {
			timezone = "Asia/Amman";
		} else if (countryCode.equals("TM")) {
			timezone = "Asia/Ashgabat";
		} else if (countryCode.equals("IQ")) {
			timezone = "Asia/Baghdad";
		} else if (countryCode.equals("BH")) {
			timezone = "Asia/Bahrain";
		} else if (countryCode.equals("AZ")) {
			timezone = "Asia/Baku";
		} else if (countryCode.equals("TH")) {
			timezone = "Asia/Bangkok";
		} else if (countryCode.equals("LB")) {
			timezone = "Asia/Beirut";
		} else if (countryCode.equals("KG")) {
			timezone = "Asia/Bishkek";
		} else if (countryCode.equals("BN")) {
			timezone = "Asia/Brunei";
		} else if (countryCode.equals("IN")) {
			timezone = "Asia/Calcutta";
		} else if (countryCode.equals("MN")) {
			timezone = "Asia/Choibalsan";
		} else if (countryCode.equals("LK")) {
			timezone = "Asia/Colombo";
		} else if (countryCode.equals("BD")) {
			timezone = "Asia/Dhaka";
		} else if (countryCode.equals("AE")) {
			timezone = "Asia/Dubai";
		} else if (countryCode.equals("TJ")) {
			timezone = "Asia/Dushanbe";
		} else if (countryCode.equals("HK")) {
			timezone = "Asia/Hong_Kong";
		} else if (countryCode.equals("TR")) {
			timezone = "Asia/Istanbul";
		} else if (countryCode.equals("IL")) {
			timezone = "Asia/Jerusalem";
		} else if (countryCode.equals("AF")) {
			timezone = "Asia/Kabul";
		} else if (countryCode.equals("PK")) {
			timezone = "Asia/Karachi";
		} else if (countryCode.equals("NP")) {
			timezone = "Asia/Katmandu";
		} else if (countryCode.equals("KW")) {
			timezone = "Asia/Kuwait";
		} else if (countryCode.equals("MO")) {
			timezone = "Asia/Macao";
		} else if (countryCode.equals("PH")) {
			timezone = "Asia/Manila";
		} else if (countryCode.equals("OM")) {
			timezone = "Asia/Muscat";
		} else if (countryCode.equals("CY")) {
			timezone = "Asia/Nicosia";
		} else if (countryCode.equals("KP")) {
			timezone = "Asia/Pyongyang";
		} else if (countryCode.equals("QA")) {
			timezone = "Asia/Qatar";
		} else if (countryCode.equals("MM")) {
			timezone = "Asia/Rangoon";
		} else if (countryCode.equals("SA")) {
			timezone = "Asia/Riyadh";
		} else if (countryCode.equals("KR")) {
			timezone = "Asia/Seoul";
		} else if (countryCode.equals("SG")) {
			timezone = "Asia/Singapore";
		} else if (countryCode.equals("TW")) {
			timezone = "Asia/Taipei";
		} else if (countryCode.equals("GE")) {
			timezone = "Asia/Tbilisi";
		} else if (countryCode.equals("BT")) {
			timezone = "Asia/Thimphu";
		} else if (countryCode.equals("JP")) {
			timezone = "Asia/Tokyo";
		} else if (countryCode.equals("LA")) {
			timezone = "Asia/Vientiane";
		} else if (countryCode.equals("AM")) {
			timezone = "Asia/Yerevan";
		} else if (countryCode.equals("BM")) {
			timezone = "Atlantic/Bermuda";
		} else if (countryCode.equals("CV")) {
			timezone = "Atlantic/Cape_Verde";
		} else if (countryCode.equals("FO")) {
			timezone = "Atlantic/Faeroe";
		} else if (countryCode.equals("IS")) {
			timezone = "Atlantic/Reykjavik";
		} else if (countryCode.equals("GS")) {
			timezone = "Atlantic/South_Georgia";
		} else if (countryCode.equals("SH")) {
			timezone = "Atlantic/St_Helena";
		} else if (countryCode.equals("CL")) {
			timezone = "Chile/Continental";
		} else if (countryCode.equals("NL")) {
			timezone = "Europe/Amsterdam";
		} else if (countryCode.equals("AD")) {
			timezone = "Europe/Andorra";
		} else if (countryCode.equals("GR")) {
			timezone = "Europe/Athens";
		} else if (countryCode.equals("YU")) {
			timezone = "Europe/Belgrade";
		} else if (countryCode.equals("DE")) {
			timezone = "Europe/Berlin";
		} else if (countryCode.equals("SK")) {
			timezone = "Europe/Bratislava";
		} else if (countryCode.equals("BE")) {
			timezone = "Europe/Brussels";
		} else if (countryCode.equals("RO")) {
			timezone = "Europe/Bucharest";
		} else if (countryCode.equals("HU")) {
			timezone = "Europe/Budapest";
		} else if (countryCode.equals("DK")) {
			timezone = "Europe/Copenhagen";
		} else if (countryCode.equals("IE")) {
			timezone = "Europe/Dublin";
		} else if (countryCode.equals("GI")) {
			timezone = "Europe/Gibraltar";
		} else if (countryCode.equals("FI")) {
			timezone = "Europe/Helsinki";
		} else if (countryCode.equals("SI")) {
			timezone = "Europe/Ljubljana";
		} else if (countryCode.equals("GB")) {
			timezone = "Europe/London";
		} else if (countryCode.equals("LU")) {
			timezone = "Europe/Luxembourg";
		} else if (countryCode.equals("MT")) {
			timezone = "Europe/Malta";
		} else if (countryCode.equals("BY")) {
			timezone = "Europe/Minsk";
		} else if (countryCode.equals("MC")) {
			timezone = "Europe/Monaco";
		} else if (countryCode.equals("NO")) {
			timezone = "Europe/Oslo";
		} else if (countryCode.equals("CZ")) {
			timezone = "Europe/Prague";
		} else if (countryCode.equals("LV")) {
			timezone = "Europe/Riga";
		} else if (countryCode.equals("IT")) {
			timezone = "Europe/Rome";
		} else if (countryCode.equals("SM")) {
			timezone = "Europe/San_Marino";
		} else if (countryCode.equals("BA")) {
			timezone = "Europe/Sarajevo";
		} else if (countryCode.equals("MK")) {
			timezone = "Europe/Skopje";
		} else if (countryCode.equals("BG")) {
			timezone = "Europe/Sofia";
		} else if (countryCode.equals("SE")) {
			timezone = "Europe/Stockholm";
		} else if (countryCode.equals("EE")) {
			timezone = "Europe/Tallinn";
		} else if (countryCode.equals("AL")) {
			timezone = "Europe/Tirane";
		} else if (countryCode.equals("LI")) {
			timezone = "Europe/Vaduz";
		} else if (countryCode.equals("VA")) {
			timezone = "Europe/Vatican";
		} else if (countryCode.equals("AT")) {
			timezone = "Europe/Vienna";
		} else if (countryCode.equals("LT")) {
			timezone = "Europe/Vilnius";
		} else if (countryCode.equals("PL")) {
			timezone = "Europe/Warsaw";
		} else if (countryCode.equals("HR")) {
			timezone = "Europe/Zagreb";
		} else if (countryCode.equals("IR")) {
			timezone = "Asia/Tehran";
		} else if (countryCode.equals("MG")) {
			timezone = "Indian/Antananarivo";
		} else if (countryCode.equals("CX")) {
			timezone = "Indian/Christmas";
		} else if (countryCode.equals("CC")) {
			timezone = "Indian/Cocos";
		} else if (countryCode.equals("KM")) {
			timezone = "Indian/Comoro";
		} else if (countryCode.equals("MV")) {
			timezone = "Indian/Maldives";
		} else if (countryCode.equals("MU")) {
			timezone = "Indian/Mauritius";
		} else if (countryCode.equals("YT")) {
			timezone = "Indian/Mayotte";
		} else if (countryCode.equals("RE")) {
			timezone = "Indian/Reunion";
		} else if (countryCode.equals("FJ")) {
			timezone = "Pacific/Fiji";
		} else if (countryCode.equals("TV")) {
			timezone = "Pacific/Funafuti";
		} else if (countryCode.equals("GU")) {
			timezone = "Pacific/Guam";
		} else if (countryCode.equals("NR")) {
			timezone = "Pacific/Nauru";
		} else if (countryCode.equals("NU")) {
			timezone = "Pacific/Niue";
		} else if (countryCode.equals("NF")) {
			timezone = "Pacific/Norfolk";
		} else if (countryCode.equals("PW")) {
			timezone = "Pacific/Palau";
		} else if (countryCode.equals("PN")) {
			timezone = "Pacific/Pitcairn";
		} else if (countryCode.equals("CK")) {
			timezone = "Pacific/Rarotonga";
		} else if (countryCode.equals("WS")) {
			timezone = "Pacific/Samoa";
		} else if (countryCode.equals("KI")) {
			timezone = "Pacific/Tarawa";
		} else if (countryCode.equals("TO")) {
			timezone = "Pacific/Tongatapu";
		} else if (countryCode.equals("WF")) {
			timezone = "Pacific/Wallis";
		} else if (countryCode.equals("TZ")) {
			timezone = "Africa/Dar_es_Salaam";
		} else if (countryCode.equals("VN")) {
			timezone = "Asia/Phnom_Penh";
		} else if (countryCode.equals("KH")) {
			timezone = "Asia/Phnom_Penh";
		} else if (countryCode.equals("CM")) {
			timezone = "Africa/Lagos";
		} else if (countryCode.equals("DO")) {
			timezone = "America/Santo_Domingo";
		} else if (countryCode.equals("ET")) {
			timezone = "Africa/Addis_Ababa";
		} else if (countryCode.equals("FX")) {
			timezone = "Europe/Paris";
		} else if (countryCode.equals("HT")) {
			timezone = "America/Port-au-Prince";
		} else if (countryCode.equals("CH")) {
			timezone = "Europe/Zurich";
		} else if (countryCode.equals("AN")) {
			timezone = "America/Curacao";
		} else if (countryCode.equals("BJ")) {
			timezone = "Africa/Porto-Novo";
		} else if (countryCode.equals("EH")) {
			timezone = "Africa/El_Aaiun";
		} else if (countryCode.equals("FK")) {
			timezone = "Atlantic/Stanley";
		} else if (countryCode.equals("GF")) {
			timezone = "America/Cayenne";
		} else if (countryCode.equals("IO")) {
			timezone = "Indian/Chagos";
		} else if (countryCode.equals("MD")) {
			timezone = "Europe/Chisinau";
		} else if (countryCode.equals("MP")) {
			timezone = "Pacific/Saipan";
		} else if (countryCode.equals("MW")) {
			timezone = "Africa/Blantyre";
		} else if (countryCode.equals("NA")) {
			timezone = "Africa/Windhoek";
		} else if (countryCode.equals("NC")) {
			timezone = "Pacific/Noumea";
		} else if (countryCode.equals("PG")) {
			timezone = "Pacific/Port_Moresby";
		} else if (countryCode.equals("PM")) {
			timezone = "America/Miquelon";
		} else if (countryCode.equals("PS")) {
			timezone = "Asia/Gaza";
		} else if (countryCode.equals("PY")) {
			timezone = "America/Asuncion";
		} else if (countryCode.equals("SB")) {
			timezone = "Pacific/Guadalcanal";
		} else if (countryCode.equals("SC")) {
			timezone = "Indian/Mahe";
		} else if (countryCode.equals("SJ")) {
			timezone = "Arctic/Longyearbyen";
		} else if (countryCode.equals("SY")) {
			timezone = "Asia/Damascus";
		} else if (countryCode.equals("TC")) {
			timezone = "America/Grand_Turk";
		} else if (countryCode.equals("TF")) {
			timezone = "Indian/Kerguelen";
		} else if (countryCode.equals("TK")) {
			timezone = "Pacific/Fakaofo";
		} else if (countryCode.equals("TT")) {
			timezone = "America/Port_of_Spain";
		} else if (countryCode.equals("VG")) {
			timezone = "America/Tortola";
		} else if (countryCode.equals("VI")) {
			timezone = "America/St_Thomas";
		} else if (countryCode.equals("VU")) {
			timezone = "Pacific/Efate";
		} else if (countryCode.equals("RS")) {
			timezone = "Europe/Belgrade";
		} else if (countryCode.equals("ME")) {
			timezone = "Europe/Podgorica";
		} else if (countryCode.equals("AX")) {
			timezone = "Europe/Mariehamn";
		} else if (countryCode.equals("GG")) {
			timezone = "Europe/Guernsey";
		} else if (countryCode.equals("IM")) {
			timezone = "Europe/Isle_of_Man";
		} else if (countryCode.equals("JE")) {
			timezone = "Europe/Jersey";
		} else if (countryCode.equals("BL")) {
			timezone = "America/St_Barthelemy";
		} else if (countryCode.equals("MF")) {
			timezone = "America/Marigot";
		} else if (countryCode.equals("AR")) {
			if (regionCode.equals("01")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("02")) {
				timezone = "America/Argentina/Catamarca";
			} else if (regionCode.equals("03")) {
				timezone = "America/Argentina/Tucuman";
			} else if (regionCode.equals("04")) {
				timezone = "America/Argentina/Rio_Gallegos";
			} else if (regionCode.equals("05")) {
				timezone = "America/Argentina/Cordoba";
			} else if (regionCode.equals("06")) {
				timezone = "America/Argentina/Tucuman";
			} else if (regionCode.equals("07")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("08")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("09")) {
				timezone = "America/Argentina/Tucuman";
			} else if (regionCode.equals("10")) {
				timezone = "America/Argentina/Jujuy";
			} else if (regionCode.equals("11")) {
				timezone = "America/Argentina/San_Luis";
			} else if (regionCode.equals("12")) {
				timezone = "America/Argentina/La_Rioja";
			} else if (regionCode.equals("13")) {
				timezone = "America/Argentina/Mendoza";
			} else if (regionCode.equals("14")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("15")) {
				timezone = "America/Argentina/San_Luis";
			} else if (regionCode.equals("16")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("17")) {
				timezone = "America/Argentina/Salta";
			} else if (regionCode.equals("18")) {
				timezone = "America/Argentina/San_Juan";
			} else if (regionCode.equals("19")) {
				timezone = "America/Argentina/San_Luis";
			} else if (regionCode.equals("20")) {
				timezone = "America/Argentina/Rio_Gallegos";
			} else if (regionCode.equals("21")) {
				timezone = "America/Argentina/Buenos_Aires";
			} else if (regionCode.equals("22")) {
				timezone = "America/Argentina/Catamarca";
			} else if (regionCode.equals("23")) {
				timezone = "America/Argentina/Ushuaia";
			} else if (regionCode.equals("24")) {
				timezone = "America/Argentina/Tucuman";
			}
		} else if (countryCode.equals("BR")) {
			if (regionCode.equals("01")) {
				timezone = "America/Rio_Branco";
			} else if (regionCode.equals("02")) {
				timezone = "America/Maceio";
			} else if (regionCode.equals("03")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("04")) {
				timezone = "America/Manaus";
			} else if (regionCode.equals("05")) {
				timezone = "America/Bahia";
			} else if (regionCode.equals("06")) {
				timezone = "America/Fortaleza";
			} else if (regionCode.equals("07")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("08")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("11")) {
				timezone = "America/Campo_Grande";
			} else if (regionCode.equals("13")) {
				timezone = "America/Belem";
			} else if (regionCode.equals("14")) {
				timezone = "America/Cuiaba";
			} else if (regionCode.equals("15")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("16")) {
				timezone = "America/Belem";
			} else if (regionCode.equals("17")) {
				timezone = "America/Recife";
			} else if (regionCode.equals("18")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("20")) {
				timezone = "America/Fortaleza";
			} else if (regionCode.equals("21")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("22")) {
				timezone = "America/Recife";
			} else if (regionCode.equals("23")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("24")) {
				timezone = "America/Porto_Velho";
			} else if (regionCode.equals("25")) {
				timezone = "America/Boa_Vista";
			} else if (regionCode.equals("26")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("27")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("28")) {
				timezone = "America/Maceio";
			} else if (regionCode.equals("29")) {
				timezone = "America/Sao_Paulo";
			} else if (regionCode.equals("30")) {
				timezone = "America/Recife";
			} else if (regionCode.equals("31")) {
				timezone = "America/Araguaina";
			}
		} else if (countryCode.equals("CD")) {
			if (regionCode.equals("02")) {
				timezone = "Africa/Kinshasa";
			} else if (regionCode.equals("05")) {
				timezone = "Africa/Lubumbashi";
			} else if (regionCode.equals("06")) {
				timezone = "Africa/Kinshasa";
			} else if (regionCode.equals("08")) {
				timezone = "Africa/Kinshasa";
			} else if (regionCode.equals("10")) {
				timezone = "Africa/Lubumbashi";
			} else if (regionCode.equals("11")) {
				timezone = "Africa/Lubumbashi";
			} else if (regionCode.equals("12")) {
				timezone = "Africa/Lubumbashi";
			}
		} else if (countryCode.equals("CN")) {
			if (regionCode.equals("01")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("04")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("05")) {
				timezone = "Asia/Harbin";
			} else if (regionCode.equals("06")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("07")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("08")) {
				timezone = "Asia/Harbin";
			} else if (regionCode.equals("09")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("10")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("11")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("12")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Urumqi";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("15")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("16")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("18")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("19")) {
				timezone = "Asia/Harbin";
			} else if (regionCode.equals("20")) {
				timezone = "Asia/Harbin";
			} else if (regionCode.equals("21")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("22")) {
				timezone = "Asia/Harbin";
			} else if (regionCode.equals("23")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("24")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("25")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("26")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("28")) {
				timezone = "Asia/Shanghai";
			} else if (regionCode.equals("29")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("30")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("31")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("32")) {
				timezone = "Asia/Chongqing";
			} else if (regionCode.equals("33")) {
				timezone = "Asia/Chongqing";
			}
		} else if (countryCode.equals("EC")) {
			if (regionCode.equals("01")) {
				timezone = "Pacific/Galapagos";
			} else if (regionCode.equals("02")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("03")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("04")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("05")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("06")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("07")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("08")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("09")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("10")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("11")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("12")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("13")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("14")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("15")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("17")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("18")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("19")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("20")) {
				timezone = "America/Guayaquil";
			} else if (regionCode.equals("22")) {
				timezone = "America/Guayaquil";
			}
		} else if (countryCode.equals("ES")) {
			if (regionCode.equals("07")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("27")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("29")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("31")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("32")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("34")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("39")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("51")) {
				timezone = "Africa/Ceuta";
			} else if (regionCode.equals("52")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("53")) {
				timezone = "Atlantic/Canary";
			} else if (regionCode.equals("54")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("55")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("56")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("57")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("58")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("59")) {
				timezone = "Europe/Madrid";
			} else if (regionCode.equals("60")) {
				timezone = "Europe/Madrid";
			}
		} else if (countryCode.equals("GL")) {
			if (regionCode.equals("01")) {
				timezone = "America/Thule";
			} else if (regionCode.equals("02")) {
				timezone = "America/Godthab";
			} else if (regionCode.equals("03")) {
				timezone = "America/Godthab";
			}
		} else if (countryCode.equals("ID")) {
			if (regionCode.equals("01")) {
				timezone = "Asia/Pontianak";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("04")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("05")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("06")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("07")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("08")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("09")) {
				timezone = "Asia/Jayapura";
			} else if (regionCode.equals("10")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("11")) {
				timezone = "Asia/Pontianak";
			} else if (regionCode.equals("12")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("15")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("16")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("17")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("18")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("19")) {
				timezone = "Asia/Pontianak";
			} else if (regionCode.equals("20")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("21")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("22")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("23")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("24")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("25")) {
				timezone = "Asia/Pontianak";
			} else if (regionCode.equals("26")) {
				timezone = "Asia/Pontianak";
			} else if (regionCode.equals("30")) {
				timezone = "Asia/Jakarta";
			} else if (regionCode.equals("31")) {
				timezone = "Asia/Makassar";
			} else if (regionCode.equals("33")) {
				timezone = "Asia/Jakarta";
			}
		} else if (countryCode.equals("KZ")) {
			if (regionCode.equals("01")) {
				timezone = "Asia/Almaty";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Almaty";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("04")) {
				timezone = "Asia/Aqtobe";
			} else if (regionCode.equals("05")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("06")) {
				timezone = "Asia/Aqtau";
			} else if (regionCode.equals("07")) {
				timezone = "Asia/Oral";
			} else if (regionCode.equals("08")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("09")) {
				timezone = "Asia/Aqtau";
			} else if (regionCode.equals("10")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("11")) {
				timezone = "Asia/Almaty";
			} else if (regionCode.equals("12")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Aqtobe";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Qyzylorda";
			} else if (regionCode.equals("15")) {
				timezone = "Asia/Almaty";
			} else if (regionCode.equals("16")) {
				timezone = "Asia/Aqtobe";
			} else if (regionCode.equals("17")) {
				timezone = "Asia/Almaty";
			}
		} else if (countryCode.equals("MX")) {
			if (regionCode.equals("01")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("02")) {
				timezone = "America/Tijuana";
			} else if (regionCode.equals("03")) {
				timezone = "America/Hermosillo";
			} else if (regionCode.equals("04")) {
				timezone = "America/Merida";
			} else if (regionCode.equals("05")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("06")) {
				timezone = "America/Chihuahua";
			} else if (regionCode.equals("07")) {
				timezone = "America/Monterrey";
			} else if (regionCode.equals("08")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("09")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("10")) {
				timezone = "America/Mazatlan";
			} else if (regionCode.equals("11")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("12")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("13")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("14")) {
				timezone = "America/Mazatlan";
			} else if (regionCode.equals("15")) {
				timezone = "America/Chihuahua";
			} else if (regionCode.equals("16")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("17")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("18")) {
				timezone = "America/Mazatlan";
			} else if (regionCode.equals("19")) {
				timezone = "America/Monterrey";
			} else if (regionCode.equals("20")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("21")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("22")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("23")) {
				timezone = "America/Cancun";
			} else if (regionCode.equals("24")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("25")) {
				timezone = "America/Mazatlan";
			} else if (regionCode.equals("26")) {
				timezone = "America/Hermosillo";
			} else if (regionCode.equals("27")) {
				timezone = "America/Merida";
			} else if (regionCode.equals("28")) {
				timezone = "America/Monterrey";
			} else if (regionCode.equals("29")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("30")) {
				timezone = "America/Mexico_City";
			} else if (regionCode.equals("31")) {
				timezone = "America/Merida";
			} else if (regionCode.equals("32")) {
				timezone = "America/Monterrey";
			}
		} else if (countryCode.equals("MY")) {
			if (regionCode.equals("01")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("04")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("05")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("06")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("07")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("08")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("09")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("11")) {
				timezone = "Asia/Kuching";
			} else if (regionCode.equals("12")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Kuala_Lumpur";
			} else if (regionCode.equals("15")) {
				timezone = "Asia/Kuching";
			} else if (regionCode.equals("16")) {
				timezone = "Asia/Kuching";
			}
		} else if (countryCode.equals("NZ")) {
			if (regionCode.equals("85")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("E7")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("E8")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("E9")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F1")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F2")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F3")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F4")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F5")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F7")) {
				timezone = "Pacific/Chatham";
			} else if (regionCode.equals("F8")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("F9")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("G1")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("G2")) {
				timezone = "Pacific/Auckland";
			} else if (regionCode.equals("G3")) {
				timezone = "Pacific/Auckland";
			}
		} else if (countryCode.equals("PT")) {
			if (regionCode.equals("02")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("03")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("04")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("05")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("06")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("07")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("08")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("09")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("10")) {
				timezone = "Atlantic/Madeira";
			} else if (regionCode.equals("11")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("13")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("14")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("16")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("17")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("18")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("19")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("20")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("21")) {
				timezone = "Europe/Lisbon";
			} else if (regionCode.equals("22")) {
				timezone = "Europe/Lisbon";
			}
		} else if (countryCode.equals("RU")) {
			if (regionCode.equals("01")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Novokuznetsk";
			} else if (regionCode.equals("04")) {
				timezone = "Asia/Novosibirsk";
			} else if (regionCode.equals("05")) {
				timezone = "Asia/Vladivostok";
			} else if (regionCode.equals("06")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("07")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("08")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("09")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("10")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("11")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("15")) {
				timezone = "Asia/Anadyr";
			} else if (regionCode.equals("16")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("17")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("18")) {
				timezone = "Asia/Krasnoyarsk";
			} else if (regionCode.equals("20")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("21")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("22")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("23")) {
				timezone = "Europe/Kaliningrad";
			} else if (regionCode.equals("24")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("25")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("26")) {
				timezone = "Asia/Kamchatka";
			} else if (regionCode.equals("27")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("28")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("29")) {
				timezone = "Asia/Novokuznetsk";
			} else if (regionCode.equals("30")) {
				timezone = "Asia/Vladivostok";
			} else if (regionCode.equals("31")) {
				timezone = "Asia/Krasnoyarsk";
			} else if (regionCode.equals("32")) {
				timezone = "Asia/Omsk";
			} else if (regionCode.equals("33")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("34")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("35")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("36")) {
				timezone = "Asia/Anadyr";
			} else if (regionCode.equals("37")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("38")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("39")) {
				timezone = "Asia/Krasnoyarsk";
			} else if (regionCode.equals("40")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("41")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("42")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("43")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("44")) {
				timezone = "Asia/Magadan";
			} else if (regionCode.equals("45")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("46")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("47")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("48")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("49")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("50")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("51")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("52")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("53")) {
				timezone = "Asia/Novosibirsk";
			} else if (regionCode.equals("54")) {
				timezone = "Asia/Omsk";
			} else if (regionCode.equals("55")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("56")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("57")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("58")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("59")) {
				timezone = "Asia/Vladivostok";
			} else if (regionCode.equals("60")) {
				timezone = "Europe/Kaliningrad";
			} else if (regionCode.equals("61")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("62")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("63")) {
				timezone = "Asia/Yakutsk";
			} else if (regionCode.equals("64")) {
				timezone = "Asia/Sakhalin";
			} else if (regionCode.equals("65")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("66")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("67")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("68")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("69")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("70")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("71")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("72")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("73")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("74")) {
				timezone = "Asia/Krasnoyarsk";
			} else if (regionCode.equals("75")) {
				timezone = "Asia/Novosibirsk";
			} else if (regionCode.equals("76")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("77")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("78")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("79")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("80")) {
				timezone = "Asia/Yekaterinburg";
			} else if (regionCode.equals("81")) {
				timezone = "Europe/Samara";
			} else if (regionCode.equals("82")) {
				timezone = "Asia/Irkutsk";
			} else if (regionCode.equals("83")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("84")) {
				timezone = "Europe/Volgograd";
			} else if (regionCode.equals("85")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("86")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("87")) {
				timezone = "Asia/Novosibirsk";
			} else if (regionCode.equals("88")) {
				timezone = "Europe/Moscow";
			} else if (regionCode.equals("89")) {
				timezone = "Asia/Vladivostok";
			}
		} else if (countryCode.equals("UA")) {
			if (regionCode.equals("01")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("02")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("03")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("04")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("05")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("06")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("07")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("08")) {
				timezone = "Europe/Simferopol";
			} else if (regionCode.equals("09")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("10")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("11")) {
				timezone = "Europe/Simferopol";
			} else if (regionCode.equals("13")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("14")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("15")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("16")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("17")) {
				timezone = "Europe/Simferopol";
			} else if (regionCode.equals("18")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("19")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("20")) {
				timezone = "Europe/Simferopol";
			} else if (regionCode.equals("21")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("22")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("23")) {
				timezone = "Europe/Kiev";
			} else if (regionCode.equals("24")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("25")) {
				timezone = "Europe/Uzhgorod";
			} else if (regionCode.equals("26")) {
				timezone = "Europe/Zaporozhye";
			} else if (regionCode.equals("27")) {
				timezone = "Europe/Kiev";
			}
		} else if (countryCode.equals("UZ")) {
			if (regionCode.equals("01")) {
				timezone = "Asia/Tashkent";
			} else if (regionCode.equals("02")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("03")) {
				timezone = "Asia/Tashkent";
			} else if (regionCode.equals("06")) {
				timezone = "Asia/Tashkent";
			} else if (regionCode.equals("07")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("08")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("09")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("10")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("12")) {
				timezone = "Asia/Samarkand";
			} else if (regionCode.equals("13")) {
				timezone = "Asia/Tashkent";
			} else if (regionCode.equals("14")) {
				timezone = "Asia/Tashkent";
			}
		} else if (countryCode.equals("TL")) {
			timezone = "Asia/Dili";
		} else if (countryCode.equals("PF")) {
			timezone = "Pacific/Marquesas";
		}
		return timezone;
	}

	private TimeZone() {
		// this is an utility class
	}

}

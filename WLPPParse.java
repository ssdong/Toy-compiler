import java.util.*;

public class WLPPParse {	
	private static String wlppCfg= 
			"35\n" +
			"AMP\n" +
			"BECOMES\n" +
		    "BOF\n" +
		    "COMMA\n" +
		    "DELETE\n" +
		    "ELSE\n" +
		    "EOF\n" +
		    "EQ\n" +
		    "GE\n" +
		    "GT\n" +
		    "ID\n" +
		    "IF\n" +
		    "INT\n" +
		    "LBRACE\n" +
		    "LBRACK\n" +
		    "LE\n" +
		    "LPAREN\n" +
		    "LT\n" +
		    "MINUS\n" +
		    "NE\n" +
		    "NEW\n" +
		    "NULL\n" +
		    "NUM\n" +
		    "PCT\n" +
		    "PLUS\n" +
		    "PRINTLN\n" +
		    "RBRACE\n" +
		    "RBRACK\n" +
		    "RETURN\n" +
		    "RPAREN\n" +
		    "SEMI\n" +
		    "SLASH\n" +
		    "STAR\n" +
		    "WAIN\n" +
		    "WHILE\n" +
		    "12\n" +
		    "S\n" +
		    "dcl\n" +
		    "dcls\n" +
		    "expr\n" +
		    "factor\n" +
		    "lvalue\n" +
		    "procedure\n" +
		    "statement\n" +
		    "statements\n" +
		    "term\n" +
     	    "test\n" +
		    "type\n" +
		    "S\n" +
		    "38\n" +
		    "S BOF procedure EOF\n" +
		    "procedure INT WAIN LPAREN dcl COMMA dcl RPAREN LBRACE dcls statements RETURN expr SEMI RBRACE\n" +
		    "type INT\n" +
		    "type INT STAR\n" +
		    "dcls\n" +
		    "dcls dcls dcl BECOMES NUM SEMI\n" +
		    "dcls dcls dcl BECOMES NULL SEMI\n" +
		    "dcl type ID\n" +
		    "statements\n" +
		    "statements statements statement\n" +
		    "statement lvalue BECOMES expr SEMI\n" +
		    "statement IF LPAREN test RPAREN LBRACE statements RBRACE ELSE LBRACE statements RBRACE\n" +
		    "statement WHILE LPAREN test RPAREN LBRACE statements RBRACE\n" +
		    "statement PRINTLN LPAREN expr RPAREN SEMI\n" +
		    "statement DELETE LBRACK RBRACK expr SEMI\n" +
		    "test expr EQ expr\n" +
		    "test expr NE expr\n" +
		    "test expr LT expr\n" +
		    "test expr LE expr\n" +
		    "test expr GE expr\n" +
		    "test expr GT expr\n" +
		    "expr term\n" +
		    "expr expr PLUS term\n" +
		    "expr expr MINUS term\n" +
		    "term factor\n" +
		    "term term STAR factor\n" +
		    "term term SLASH factor\n" +
		    "term term PCT factor\n" +
		    "factor ID\n" +
		    "factor NUM\n" +
		    "factor NULL\n" +
		    "factor LPAREN expr RPAREN\n" +
		    "factor AMP lvalue\n" +
		    "factor STAR factor\n" +
		    "factor NEW INT LBRACK expr RBRACK\n" +
		    "lvalue ID\n" +
		    "lvalue STAR factor\n" +
		    "lvalue LPAREN lvalue RPAREN\n";
	private static String wlppRules =
		    "35\n" +
		    "AMP\n" +
		    "BECOMES\n" +
		    "BOF\n" +
		    "COMMA\n" +
		    "DELETE\n" +
		    "ELSE\n" +
		    "EOF\n" +
		    "EQ\n" +
		    "GE\n" +
		    "GT\n" +
		    "ID\n" +
		    "IF\n" +
		    "INT\n" +
		    "LBRACE\n" +
		    "LBRACK\n" +
		    "LE\n" +
		    "LPAREN\n" +
		    "LT\n" +
		    "MINUS\n" +
		    "NE\n" +
		    "NEW\n" +
		    "NULL\n" +
		    "NUM\n" +
		    "PCT\n" +
		    "PLUS\n" +
		    "PRINTLN\n" +
		    "RBRACE\n" +
		    "RBRACK\n" +
		    "RETURN\n" +
		    "RPAREN\n" +
		    "SEMI\n" +
		    "SLASH\n" +
		    "STAR\n" +
		    "WAIN\n" +
		    "WHILE\n" +
		    "12\n" +
		    "S\n" +
		    "dcl\n" +
		    "dcls\n" +
		    "expr\n" +
		    "factor\n" +
		    "lvalue\n" +
		    "procedure\n" +
		    "statement\n" +
		    "statements\n" +
		    "term\n" +
		    "test\n" +
		    "type\n" +
		    "S\n" +
		    "38\n" +
		    "S BOF procedure EOF\n" +
		    "procedure INT WAIN LPAREN dcl COMMA dcl RPAREN LBRACE dcls statements RETURN expr SEMI RBRACE\n" +
		    "type INT\n" +
		    "type INT STAR\n" +
		    "dcls\n" +
		    "dcls dcls dcl BECOMES NUM SEMI\n" +
		    "dcls dcls dcl BECOMES NULL SEMI\n" +
		    "dcl type ID\n" +
		    "statements\n" +
		    "statements statements statement\n" +
		    "statement lvalue BECOMES expr SEMI\n" +
		    "statement IF LPAREN test RPAREN LBRACE statements RBRACE ELSE LBRACE statements RBRACE\n" +
		    "statement WHILE LPAREN test RPAREN LBRACE statements RBRACE\n" +
		    "statement PRINTLN LPAREN expr RPAREN SEMI\n" +
		    "statement DELETE LBRACK RBRACK expr SEMI\n" +
		    "test expr EQ expr\n" +
		    "test expr NE expr\n" +
		    "test expr LT expr\n" +
		    "test expr LE expr\n" +
		    "test expr GE expr\n" +
		    "test expr GT expr\n" +
		    "expr term\n" +
		    "expr expr PLUS term\n" +
		    "expr expr MINUS term\n" +
		    "term factor\n" +
		    "term term STAR factor\n" +
		    "term term SLASH factor\n" +
		    "term term PCT factor\n" +
		    "factor ID\n" +
		    "factor NUM\n" +
		    "factor NULL\n" +
		    "factor LPAREN expr RPAREN\n" +
		    "factor AMP lvalue\n" +
		    "factor STAR factor\n" +
		    "factor NEW INT LBRACK expr RBRACK\n" +
		    "lvalue ID\n" +
		    "lvalue STAR factor\n" +
		    "lvalue LPAREN lvalue RPAREN\n" +
		    "107\n" +
		    "710\n" +
		    "6 NE reduce 33\n" +
		    "90 NE reduce 32\n" +
		    "46 NUM shift 1\n" +
		    "54 NUM shift 1\n" +
		    "32 NUM shift 1\n" +
		    "36 NE reduce 31\n" +
		    "80 NE reduce 34\n" +
		    "80 MINUS reduce 34\n" +
		    "30 STAR shift 2\n" +
		    "103 RETURN reduce 8\n" +
		    "59 EQ reduce 36\n" +
		    "12 NE reduce 28\n" +
		    "1 NE reduce 29\n" +
		    "11 NE reduce 30\n" +
		    "53 EQ reduce 35\n" +
		    "47 NEW shift 3\n" +
		    "48 NEW shift 3\n" +
		    "12 MINUS reduce 28\n" +
		    "1 MINUS reduce 29\n" +
		    "11 MINUS reduce 30\n" +
		    "6 MINUS reduce 33\n" +
		    "90 MINUS reduce 32\n" +
		    "45 EQ reduce 37\n" +
		    "36 MINUS reduce 31\n" +
		    "101 RETURN reduce 8\n" +
		    "100 RETURN reduce 8\n" +
		    "34 SEMI shift 4\n" +
		    "51 SEMI shift 5\n" +
		    "4 WHILE reduce 6\n" +
		    "5 WHILE reduce 5\n" +
		    "85 STAR reduce 4\n" +
		    "67 PRINTLN reduce 8\n" +
		    "14 factor shift 6\n" +
		    "6 RBRACK reduce 33\n" +
		    "90 RBRACK reduce 32\n" +
		    "23 GT shift 7\n" +
		    "71 RBRACE shift 8\n" +
		    "72 RBRACE shift 9\n" +
		    "66 RETURN reduce 9\n" +
		    "36 RBRACK reduce 31\n" +
		    "80 RBRACK reduce 34\n" +
		    "73 RBRACE shift 10\n" +
		    "12 RBRACK reduce 28\n" +
		    "1 RBRACK reduce 29\n" +
		    "11 RBRACK reduce 30\n" +
		    "4 INT reduce 6\n" +
		    "5 INT reduce 5\n" +
		    "83 NULL shift 11\n" +
		    "13 NULL shift 11\n" +
		    "7 ID shift 12\n" +
		    "16 ID shift 12\n" +
		    "24 ID shift 12\n" +
		    "21 ID shift 12\n" +
		    "37 ID shift 12\n" +
		    "17 ID shift 12\n" +
		    "81 BECOMES reduce 7\n" +
		    "83 LPAREN shift 13\n" +
		    "46 STAR shift 14\n" +
		    "54 STAR shift 14\n" +
		    "32 STAR shift 14\n" +
		    "83 factor shift 15\n" +
		    "45 GT reduce 37\n" +
		    "13 factor shift 15\n" +
		    "59 GT reduce 36\n" +
		    "13 LPAREN shift 13\n" +
		    "53 GT reduce 35\n" +
		    "23 GE shift 16\n" +
		    "53 GE reduce 35\n" +
		    "59 GE reduce 36\n" +
		    "85 PRINTLN reduce 4\n" +
		    "45 GE reduce 37\n" +
		    "23 EQ shift 17\n" +
		    "68 BECOMES shift 18\n" +
		    "19 RBRACK reduce 21\n" +
		    "78 RBRACK reduce 22\n" +
		    "79 RBRACK reduce 23\n" +
		    "49 NUM shift 1\n" +
		    "18 NUM shift 1\n" +
		    "2 LPAREN shift 13\n" +
		    "53 PCT reduce 35\n" +
		    "27 NUM shift 1\n" +
		    "2 STAR shift 14\n" +
		    "84 term shift 19\n" +
		    "59 PCT reduce 36\n" +
		    "45 PCT reduce 37\n" +
		    "56 RPAREN shift 20\n" +
		    "23 LT shift 21\n" +
		    "95 PLUS shift 22\n" +
		    "94 PLUS shift 22\n" +
		    "93 PLUS shift 22\n" +
		    "92 PLUS shift 22\n" +
		    "91 PLUS shift 22\n" +
		    "96 PLUS shift 22\n" +
		    "83 NUM shift 1\n" +
		    "47 expr shift 23\n" +
		    "48 expr shift 23\n" +
		    "59 BECOMES reduce 36\n" +
		    "53 BECOMES reduce 35\n" +
		    "45 BECOMES reduce 37\n" +
		    "84 ID shift 12\n" +
		    "47 NULL shift 11\n" +
		    "48 NULL shift 11\n" +
		    "14 LPAREN shift 13\n" +
		    "80 SEMI reduce 34\n" +
		    "23 LE shift 24\n" +
		    "89 MINUS shift 25\n" +
		    "36 SEMI reduce 31\n" +
		    "88 MINUS shift 25\n" +
		    "13 STAR shift 14\n" +
		    "84 STAR shift 14\n" +
		    "13 NUM shift 1\n" +
		    "83 STAR shift 14\n" +
		    "27 term shift 19\n" +
		    "49 term shift 19\n" +
		    "18 term shift 19\n" +
		    "25 AMP shift 26\n" +
		    "22 AMP shift 26\n" +
		    "67 RETURN reduce 8\n" +
		    "10 DELETE reduce 11\n" +
		    "105 RBRACE reduce 10\n" +
		    "7 STAR shift 14\n" +
		    "16 STAR shift 14\n" +
		    "24 STAR shift 14\n" +
		    "21 STAR shift 14\n" +
		    "37 STAR shift 14\n" +
		    "17 STAR shift 14\n" +
		    "74 RBRACE reduce 13\n" +
		    "106 RBRACE reduce 14\n" +
		    "66 WHILE reduce 9\n" +
		    "9 DELETE reduce 12\n" +
		    "9 RBRACE reduce 12\n" +
		    "104 RBRACK shift 27\n" +
		    "6 SEMI reduce 33\n" +
		    "6 STAR reduce 33\n" +
		    "90 SEMI reduce 32\n" +
		    "90 STAR reduce 32\n" +
		    "10 RBRACE reduce 11\n" +
		    "12 SEMI reduce 28\n" +
		    "12 STAR reduce 28\n" +
		    "1 SEMI reduce 29\n" +
		    "1 STAR reduce 29\n" +
		    "11 SEMI reduce 30\n" +
		    "11 STAR reduce 30\n" +
		    "53 LE reduce 35\n" +
		    "36 STAR reduce 31\n" +
		    "80 STAR reduce 34\n" +
		    "64 SEMI shift 28\n" +
		    "74 PRINTLN reduce 13\n" +
		    "106 PRINTLN reduce 14\n" +
		    "105 PRINTLN reduce 10\n" +
		    "40 PLUS reduce 25\n" +
		    "41 PLUS reduce 26\n" +
		    "42 PLUS reduce 27\n" +
		    "9 PRINTLN reduce 12\n" +
		    "2 NUM shift 1\n" +
		    "15 PLUS reduce 24\n" +
		    "10 PRINTLN reduce 11\n" +
		    "27 LPAREN shift 13\n" +
		    "88 PLUS shift 22\n" +
		    "7 NULL shift 11\n" +
		    "16 NULL shift 11\n" +
		    "24 NULL shift 11\n" +
		    "21 NULL shift 11\n" +
		    "37 NULL shift 11\n" +
		    "17 NULL shift 11\n" +
		    "89 PLUS shift 22\n" +
		    "7 NUM shift 1\n" +
		    "16 NUM shift 1\n" +
		    "24 NUM shift 1\n" +
		    "21 NUM shift 1\n" +
		    "37 NUM shift 1\n" +
		    "17 NUM shift 1\n" +
		    "49 LPAREN shift 13\n" +
		    "18 LPAREN shift 13\n" +
		    "101 STAR reduce 8\n" +
		    "100 STAR reduce 8\n" +
		    "67 STAR reduce 8\n" +
		    "15 SEMI reduce 24\n" +
		    "103 STAR reduce 8\n" +
		    "39 STAR shift 29\n" +
		    "40 SEMI reduce 25\n" +
		    "41 SEMI reduce 26\n" +
		    "42 SEMI reduce 27\n" +
		    "80 RPAREN reduce 34\n" +
		    "67 WHILE reduce 8\n" +
		    "105 DELETE reduce 10\n" +
		    "74 DELETE reduce 13\n" +
		    "106 DELETE reduce 14\n" +
		    "12 RPAREN reduce 28\n" +
		    "1 RPAREN reduce 29\n" +
		    "11 RPAREN reduce 30\n" +
		    "6 RPAREN reduce 33\n" +
		    "90 RPAREN reduce 32\n" +
		    "47 STAR shift 14\n" +
		    "48 STAR shift 14\n" +
		    "36 RPAREN reduce 31\n" +
		    "19 LT reduce 21\n" +
		    "45 PLUS reduce 37\n" +
		    "97 LPAREN shift 30\n" +
		    "78 LT reduce 22\n" +
		    "79 LT reduce 23\n" +
		    "53 PLUS reduce 35\n" +
		    "59 PLUS reduce 36\n" +
		    "15 NE reduce 24\n" +
		    "64 PLUS shift 22\n" +
		    "40 NE reduce 25\n" +
		    "41 NE reduce 26\n" +
		    "42 NE reduce 27\n" +
		    "28 RBRACE shift 31\n" +
		    "39 ID reduce 2\n" +
		    "14 NEW shift 3\n" +
		    "30 LPAREN shift 30\n" +
		    "80 EQ reduce 34\n" +
		    "12 GT reduce 28\n" +
		    "1 GT reduce 29\n" +
		    "11 GT reduce 30\n" +
		    "36 GT reduce 31\n" +
		    "6 GT reduce 33\n" +
		    "90 GT reduce 32\n" +
		    "78 RPAREN reduce 22\n" +
		    "79 RPAREN reduce 23\n" +
		    "19 RPAREN reduce 21\n" +
		    "36 GE reduce 31\n" +
		    "80 GE reduce 34\n" +
		    "67 ID reduce 8\n" +
		    "10 WHILE reduce 11\n" +
		    "67 IF reduce 8\n" +
		    "66 RBRACE reduce 9\n" +
		    "12 GE reduce 28\n" +
		    "1 GE reduce 29\n" +
		    "11 GE reduce 30\n" +
		    "6 GE reduce 33\n" +
		    "90 GE reduce 32\n" +
		    "78 NE reduce 22\n" +
		    "79 NE reduce 23\n" +
		    "19 NE reduce 21\n" +
		    "4 STAR reduce 6\n" +
		    "5 STAR reduce 5\n" +
		    "9 WHILE reduce 12\n" +
		    "25 NEW shift 3\n" +
		    "22 NEW shift 3\n" +
		    "74 WHILE reduce 13\n" +
		    "106 WHILE reduce 14\n" +
		    "105 WHILE reduce 10\n" +
		    "101 IF reduce 8\n" +
		    "100 IF reduce 8\n" +
		    "103 ID reduce 8\n" +
		    "73 STAR shift 2\n" +
		    "103 IF reduce 8\n" +
		    "79 PCT shift 32\n" +
		    "78 PCT shift 32\n" +
		    "53 RBRACK reduce 35\n" +
		    "59 RBRACK reduce 36\n" +
		    "45 RBRACK reduce 37\n" +
		    "101 ID reduce 8\n" +
		    "100 ID reduce 8\n" +
		    "3 INT shift 33\n" +
		    "71 STAR shift 2\n" +
		    "72 STAR shift 2\n" +
		    "85 LPAREN reduce 4\n" +
		    "46 ID shift 12\n" +
		    "54 ID shift 12\n" +
		    "32 ID shift 12\n" +
		    "66 LPAREN reduce 9\n" +
		    "80 GT reduce 34\n" +
		    "85 IF reduce 4\n" +
		    "43 NULL shift 34\n" +
		    "85 ID reduce 4\n" +
		    "8 ELSE shift 35\n" +
		    "66 DELETE reduce 9\n" +
		    "89 RPAREN shift 36\n" +
		    "23 NE shift 37\n" +
		    "97 WHILE shift 38\n" +
		    "19 LE reduce 21\n" +
		    "14 AMP shift 26\n" +
		    "98 INT shift 39\n" +
		    "70 INT shift 39\n" +
		    "71 LPAREN shift 30\n" +
		    "72 LPAREN shift 30\n" +
		    "46 factor shift 40\n" +
		    "54 factor shift 41\n" +
		    "32 factor shift 42\n" +
		    "81 RPAREN reduce 7\n" +
		    "13 term shift 19\n" +
		    "78 LE reduce 22\n" +
		    "79 LE reduce 23\n" +
		    "83 term shift 19\n" +
		    "73 LPAREN shift 30\n" +
		    "58 BECOMES shift 43\n" +
		    "80 PCT reduce 34\n" +
		    "12 PCT reduce 28\n" +
		    "1 PCT reduce 29\n" +
		    "11 PCT reduce 30\n" +
		    "6 PCT reduce 33\n" +
		    "90 PCT reduce 32\n" +
		    "36 PCT reduce 31\n" +
		    "40 MINUS reduce 25\n" +
		    "41 MINUS reduce 26\n" +
		    "42 MINUS reduce 27\n" +
		    "15 MINUS reduce 24\n" +
		    "99 INT shift 44\n" +
		    "2 AMP shift 26\n" +
		    "15 STAR reduce 24\n" +
		    "45 RPAREN reduce 37\n" +
		    "7 factor shift 15\n" +
		    "16 factor shift 15\n" +
		    "24 factor shift 15\n" +
		    "21 factor shift 15\n" +
		    "37 factor shift 15\n" +
		    "17 factor shift 15\n" +
		    "9 LPAREN reduce 12\n" +
		    "105 LPAREN reduce 10\n" +
		    "74 LPAREN reduce 13\n" +
		    "106 LPAREN reduce 14\n" +
		    "53 RPAREN reduce 35\n" +
		    "69 RPAREN shift 45\n" +
		    "59 RPAREN reduce 36\n" +
		    "84 NUM shift 1\n" +
		    "10 LPAREN reduce 11\n" +
		    "19 STAR shift 46\n" +
		    "79 STAR shift 46\n" +
		    "78 STAR shift 46\n" +
		    "47 LPAREN shift 13\n" +
		    "48 LPAREN shift 13\n" +
		    "52 LPAREN shift 47\n" +
		    "38 LPAREN shift 48\n" +
		    "82 LPAREN shift 49\n" +
		    "19 MINUS reduce 21\n" +
		    "99 procedure shift 50\n" +
		    "78 MINUS reduce 22\n" +
		    "79 MINUS reduce 23\n" +
		    "36 LT reduce 31\n" +
		    "12 LT reduce 28\n" +
		    "1 LT reduce 29\n" +
		    "11 LT reduce 30\n" +
		    "67 INT shift 39\n" +
		    "6 LT reduce 33\n" +
		    "90 LT reduce 32\n" +
		    "80 LT reduce 34\n" +
		    "78 GE reduce 22\n" +
		    "79 GE reduce 23\n" +
		    "43 NUM shift 51\n" +
		    "97 IF shift 52\n" +
		    "19 GE reduce 21\n" +
		    "15 RBRACK reduce 24\n" +
		    "36 LE reduce 31\n" +
		    "6 LE reduce 33\n" +
		    "90 LE reduce 32\n" +
		    "23 PLUS shift 22\n" +
		    "40 RBRACK reduce 25\n" +
		    "41 RBRACK reduce 26\n" +
		    "42 RBRACK reduce 27\n" +
		    "12 LE reduce 28\n" +
		    "1 LE reduce 29\n" +
		    "11 LE reduce 30\n" +
		    "80 LE reduce 34\n" +
		    "97 ID shift 53\n" +
		    "47 ID shift 12\n" +
		    "48 ID shift 12\n" +
		    "78 GT reduce 22\n" +
		    "79 GT reduce 23\n" +
		    "19 SLASH shift 54\n" +
		    "19 GT reduce 21\n" +
		    "79 SLASH shift 54\n" +
		    "78 SLASH shift 54\n" +
		    "84 NULL shift 11\n" +
		    "13 AMP shift 26\n" +
		    "31 EOF reduce 1\n" +
		    "83 AMP shift 26\n" +
		    "40 STAR reduce 25\n" +
		    "41 STAR reduce 26\n" +
		    "42 STAR reduce 27\n" +
		    "67 RBRACE reduce 8\n" +
		    "15 EQ reduce 24\n" +
		    "40 EQ reduce 25\n" +
		    "41 EQ reduce 26\n" +
		    "42 EQ reduce 27\n" +
		    "27 expr shift 55\n" +
		    "49 expr shift 56\n" +
		    "18 expr shift 57\n" +
		    "103 RBRACE reduce 8\n" +
		    "67 dcl shift 58\n" +
		    "13 ID shift 12\n" +
		    "46 AMP shift 26\n" +
		    "54 AMP shift 26\n" +
		    "32 AMP shift 26\n" +
		    "83 ID shift 12\n" +
		    "2 factor shift 59\n" +
		    "4 PRINTLN reduce 6\n" +
		    "5 PRINTLN reduce 5\n" +
		    "98 dcl shift 60\n" +
		    "97 STAR shift 2\n" +
		    "71 DELETE shift 61\n" +
		    "72 DELETE shift 61\n" +
		    "80 SLASH reduce 34\n" +
		    "70 dcl shift 62\n" +
		    "27 ID shift 12\n" +
		    "49 ID shift 12\n" +
		    "18 ID shift 12\n" +
		    "67 type shift 63\n" +
		    "36 PLUS reduce 31\n" +
		    "6 SLASH reduce 33\n" +
		    "90 SLASH reduce 32\n" +
		    "36 SLASH reduce 31\n" +
		    "80 PLUS reduce 34\n" +
		    "67 LPAREN reduce 8\n" +
		    "12 SLASH reduce 28\n" +
		    "1 SLASH reduce 29\n" +
		    "11 SLASH reduce 30\n" +
		    "12 PLUS reduce 28\n" +
		    "1 PLUS reduce 29\n" +
		    "11 PLUS reduce 30\n" +
		    "6 PLUS reduce 33\n" +
		    "90 PLUS reduce 32\n" +
		    "15 GT reduce 24\n" +
		    "40 GT reduce 25\n" +
		    "41 GT reduce 26\n" +
		    "42 GT reduce 27\n" +
		    "26 LPAREN shift 30\n" +
		    "70 type shift 63\n" +
		    "98 type shift 63\n" +
		    "23 MINUS shift 25\n" +
		    "15 GE reduce 24\n" +
		    "84 expr shift 64\n" +
		    "40 GE reduce 25\n" +
		    "41 GE reduce 26\n" +
		    "42 GE reduce 27\n" +
		    "45 STAR reduce 37\n" +
		    "59 STAR reduce 36\n" +
		    "53 STAR reduce 35\n" +
		    "73 DELETE shift 61\n" +
		    "103 WHILE reduce 8\n" +
		    "85 DELETE reduce 4\n" +
		    "60 RPAREN shift 65\n" +
		    "101 RBRACE reduce 8\n" +
		    "100 RBRACE reduce 8\n" +
		    "71 statement shift 66\n" +
		    "72 statement shift 66\n" +
		    "49 factor shift 15\n" +
		    "18 factor shift 15\n" +
		    "7 AMP shift 26\n" +
		    "16 AMP shift 26\n" +
		    "24 AMP shift 26\n" +
		    "21 AMP shift 26\n" +
		    "37 AMP shift 26\n" +
		    "17 AMP shift 26\n" +
		    "27 factor shift 15\n" +
		    "73 statement shift 66\n" +
		    "101 WHILE reduce 8\n" +
		    "100 WHILE reduce 8\n" +
		    "85 dcls shift 67\n" +
		    "97 lvalue shift 68\n" +
		    "85 WHILE reduce 4\n" +
		    "84 NEW shift 3\n" +
		    "85 RETURN reduce 4\n" +
		    "66 ID reduce 9\n" +
		    "66 IF reduce 9\n" +
		    "2 NEW shift 3\n" +
		    "30 lvalue shift 69\n" +
		    "75 LPAREN shift 70\n" +
		    "47 factor shift 15\n" +
		    "48 factor shift 15\n" +
		    "13 NEW shift 3\n" +
		    "101 statements shift 71\n" +
		    "100 statements shift 72\n" +
		    "83 NEW shift 3\n" +
		    "103 statements shift 73\n" +
		    "27 NEW shift 3\n" +
		    "49 NEW shift 3\n" +
		    "18 NEW shift 3\n" +
		    "25 ID shift 12\n" +
		    "22 ID shift 12\n" +
		    "26 STAR shift 2\n" +
		    "2 ID shift 12\n" +
		    "66 STAR reduce 9\n" +
		    "103 PRINTLN reduce 8\n" +
		    "47 AMP shift 26\n" +
		    "48 AMP shift 26\n" +
		    "14 ID shift 12\n" +
		    "101 PRINTLN reduce 8\n" +
		    "100 PRINTLN reduce 8\n" +
		    "19 PCT shift 32\n" +
		    "4 LPAREN reduce 6\n" +
		    "5 LPAREN reduce 5\n" +
		    "71 lvalue shift 68\n" +
		    "72 lvalue shift 68\n" +
		    "73 lvalue shift 68\n" +
		    "36 EQ reduce 31\n" +
		    "20 SEMI shift 74\n" +
		    "12 EQ reduce 28\n" +
		    "1 EQ reduce 29\n" +
		    "11 EQ reduce 30\n" +
		    "49 NULL shift 11\n" +
		    "18 NULL shift 11\n" +
		    "6 EQ reduce 33\n" +
		    "90 EQ reduce 32\n" +
		    "27 NULL shift 11\n" +
		    "40 LT reduce 25\n" +
		    "41 LT reduce 26\n" +
		    "42 LT reduce 27\n" +
		    "15 LT reduce 24\n" +
		    "81 COMMA reduce 7\n" +
		    "10 RETURN reduce 11\n" +
		    "15 LE reduce 24\n" +
		    "97 statement shift 66\n" +
		    "44 WAIN shift 75\n" +
		    "59 MINUS reduce 36\n" +
		    "53 MINUS reduce 35\n" +
		    "15 RPAREN reduce 24\n" +
		    "45 MINUS reduce 37\n" +
		    "40 LE reduce 25\n" +
		    "41 LE reduce 26\n" +
		    "42 LE reduce 27\n" +
		    "105 RETURN reduce 10\n" +
		    "40 RPAREN reduce 25\n" +
		    "41 RPAREN reduce 26\n" +
		    "42 RPAREN reduce 27\n" +
		    "7 term shift 19\n" +
		    "16 term shift 19\n" +
		    "24 term shift 19\n" +
		    "21 term shift 19\n" +
		    "37 term shift 19\n" +
		    "17 term shift 19\n" +
		    "74 RETURN reduce 13\n" +
		    "106 RETURN reduce 14\n" +
		    "7 NEW shift 3\n" +
		    "16 NEW shift 3\n" +
		    "24 NEW shift 3\n" +
		    "21 NEW shift 3\n" +
		    "37 NEW shift 3\n" +
		    "17 NEW shift 3\n" +
		    "9 RETURN reduce 12\n" +
		    "66 PRINTLN reduce 9\n" +
		    "53 LT reduce 35\n" +
		    "59 LT reduce 36\n" +
		    "15 PCT reduce 24\n" +
		    "47 test shift 76\n" +
		    "48 test shift 77\n" +
		    "59 LE reduce 36\n" +
		    "45 LE reduce 37\n" +
		    "9 STAR reduce 12\n" +
		    "22 term shift 78\n" +
		    "25 term shift 79\n" +
		    "40 PCT reduce 25\n" +
		    "41 PCT reduce 26\n" +
		    "42 PCT reduce 27\n" +
		    "10 STAR reduce 11\n" +
		    "101 LPAREN reduce 8\n" +
		    "100 LPAREN reduce 8\n" +
		    "88 RBRACK shift 80\n" +
		    "74 STAR reduce 13\n" +
		    "106 STAR reduce 14\n" +
		    "103 LPAREN reduce 8\n" +
		    "45 LT reduce 37\n" +
		    "105 STAR reduce 10\n" +
		    "97 DELETE shift 61\n" +
		    "103 DELETE reduce 8\n" +
		    "63 ID shift 81\n" +
		    "64 MINUS shift 25\n" +
		    "101 DELETE reduce 8\n" +
		    "100 DELETE reduce 8\n" +
		    "47 term shift 19\n" +
		    "48 term shift 19\n" +
		    "97 PRINTLN shift 82\n" +
		    "71 ID shift 53\n" +
		    "72 ID shift 53\n" +
		    "45 NE reduce 37\n" +
		    "25 NUM shift 1\n" +
		    "22 NUM shift 1\n" +
		    "71 IF shift 52\n" +
		    "72 IF shift 52\n" +
		    "59 NE reduce 36\n" +
		    "53 NE reduce 35\n" +
		    "73 ID shift 53\n" +
		    "73 IF shift 52\n" +
		    "30 ID shift 53\n" +
		    "7 LPAREN shift 13\n" +
		    "16 LPAREN shift 13\n" +
		    "24 LPAREN shift 13\n" +
		    "21 LPAREN shift 13\n" +
		    "37 LPAREN shift 13\n" +
		    "17 LPAREN shift 13\n" +
		    "25 STAR shift 14\n" +
		    "22 STAR shift 14\n" +
		    "57 PLUS shift 22\n" +
		    "56 PLUS shift 22\n" +
		    "55 PLUS shift 22\n" +
		    "40 SLASH reduce 25\n" +
		    "41 SLASH reduce 26\n" +
		    "42 SLASH reduce 27\n" +
		    "15 SLASH reduce 24\n" +
		    "33 LBRACK shift 83\n" +
		    "78 PLUS reduce 22\n" +
		    "79 PLUS reduce 23\n" +
		    "97 RETURN shift 84\n" +
		    "55 MINUS shift 25\n" +
		    "57 MINUS shift 25\n" +
		    "56 MINUS shift 25\n" +
		    "19 PLUS reduce 21\n" +
		    "67 DELETE reduce 8\n" +
		    "46 LPAREN shift 13\n" +
		    "54 LPAREN shift 13\n" +
		    "32 LPAREN shift 13\n" +
		    "45 SEMI reduce 37\n" +
		    "59 SEMI reduce 36\n" +
		    "53 SEMI reduce 35\n" +
		    "49 AMP shift 26\n" +
		    "18 AMP shift 26\n" +
		    "27 AMP shift 26\n" +
		    "95 MINUS shift 25\n" +
		    "94 MINUS shift 25\n" +
		    "93 MINUS shift 25\n" +
		    "92 MINUS shift 25\n" +
		    "91 MINUS shift 25\n" +
		    "96 MINUS shift 25\n" +
		    "65 LBRACE shift 85\n" +
		    "71 WHILE shift 38\n" +
		    "72 WHILE shift 38\n" +
		    "4 DELETE reduce 6\n" +
		    "5 DELETE reduce 5\n" +
		    "73 WHILE shift 38\n" +
		    "22 factor shift 15\n" +
		    "25 factor shift 15\n" +
		    "47 NUM shift 1\n" +
		    "48 NUM shift 1\n" +
		    "76 RPAREN shift 86\n" +
		    "77 RPAREN shift 87\n" +
		    "2 NULL shift 11\n" +
		    "26 ID shift 53\n" +
		    "46 NEW shift 3\n" +
		    "54 NEW shift 3\n" +
		    "32 NEW shift 3\n" +
		    "19 SEMI reduce 21\n" +
		    "83 expr shift 88\n" +
		    "13 expr shift 89\n" +
		    "4 IF reduce 6\n" +
		    "5 IF reduce 5\n" +
		    "78 SEMI reduce 22\n" +
		    "79 SEMI reduce 23\n" +
		    "14 STAR shift 14\n" +
		    "26 lvalue shift 90\n" +
		    "4 ID reduce 6\n" +
		    "5 ID reduce 5\n" +
		    "36 BECOMES reduce 31\n" +
		    "25 LPAREN shift 13\n" +
		    "49 STAR shift 14\n" +
		    "18 STAR shift 14\n" +
		    "22 LPAREN shift 13\n" +
		    "6 BECOMES reduce 33\n" +
		    "90 BECOMES reduce 32\n" +
		    "12 BECOMES reduce 28\n" +
		    "1 BECOMES reduce 29\n" +
		    "11 BECOMES reduce 30\n" +
		    "27 STAR shift 14\n" +
		    "29 ID reduce 3\n" +
		    "10 IF reduce 11\n" +
		    "10 ID reduce 11\n" +
		    "9 IF reduce 12\n" +
		    "105 IF reduce 10\n" +
		    "74 IF reduce 13\n" +
		    "106 IF reduce 14\n" +
		    "9 ID reduce 12\n" +
		    "14 NULL shift 11\n" +
		    "80 BECOMES reduce 34\n" +
		    "95 RPAREN reduce 16\n" +
		    "94 RPAREN reduce 17\n" +
		    "93 RPAREN reduce 18\n" +
		    "92 RPAREN reduce 19\n" +
		    "91 RPAREN reduce 20\n" +
		    "96 RPAREN reduce 15\n" +
		    "46 NULL shift 11\n" +
		    "54 NULL shift 11\n" +
		    "32 NULL shift 11\n" +
		    "14 NUM shift 1\n" +
		    "7 expr shift 91\n" +
		    "16 expr shift 92\n" +
		    "24 expr shift 93\n" +
		    "21 expr shift 94\n" +
		    "37 expr shift 95\n" +
		    "17 expr shift 96\n" +
		    "4 RETURN reduce 6\n" +
		    "5 RETURN reduce 5\n" +
		    "67 statements shift 97\n" +
		    "74 ID reduce 13\n" +
		    "106 ID reduce 14\n" +
		    "105 ID reduce 10\n" +
		    "62 COMMA shift 98\n" +
		    "0 BOF shift 99\n" +
		    "19 EQ reduce 21\n" +
		    "78 EQ reduce 22\n" +
		    "79 EQ reduce 23\n" +
		    "45 SLASH reduce 37\n" +
		    "87 LBRACE shift 100\n" +
		    "86 LBRACE shift 101\n" +
		    "50 EOF shift 102\n" +
		    "59 SLASH reduce 36\n" +
		    "84 LPAREN shift 13\n" +
		    "35 LBRACE shift 103\n" +
		    "53 SLASH reduce 35\n" +
		    "61 LBRACK shift 104\n" +
		    "73 PRINTLN shift 82\n" +
		    "57 SEMI shift 105\n" +
		    "85 INT reduce 4\n" +
		    "55 SEMI shift 106\n" +
		    "84 AMP shift 26\n" +
		    "71 PRINTLN shift 82\n" +
		    "72 PRINTLN shift 82\n" +
		    "25 NULL shift 11\n" +
		    "84 factor shift 15\n" +
		    "22 NULL shift 11\n";
	private static Map<Integer,ArrayList<ArrayList<String>>> dfa = 
            new HashMap<Integer,ArrayList<ArrayList<String>>>();
	private static Set<String> terminalSymbols = new HashSet<String>();
	private static ArrayList<ArrayList<String>> productionRules = new ArrayList<ArrayList<String>>();
	private static Stack<String> symStack = new Stack<String>();
	private static Stack<Integer> stateStack = new Stack<Integer>();
	private static LinkedList<String> parsedLine = new LinkedList<String>();
	private static ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	private static LinkedList<ArrayList<String>> scannedInput = new LinkedList<ArrayList<String>>();
	
	private static String start;
	private static String reverseRightMost = "";
	private static int length;
	public static void main(String[] args) {
		Scanner wlppRule = new Scanner(wlppRules);
		readInput();
		skipGrammarAndInitialize(wlppRule);
		boolean valid = parse();
		if(!valid) {
			return;
		}
		reverseRightMost = wlppCfg + reverseRightMost;
		CFG cfg = new CFG(reverseRightMost);
		cfg.go();
		print(cfg.getDerivation());
	}

	private static void print(LinkedList<String> derivation) {
		System.out.println(derivation.removeFirst());
		System.out.println("BOF BOF");
		System.out.println(derivation.getFirst());
		
		// get the procedure main line and put it in a LinkedList<String>
		String procedureLine = derivation.removeFirst();
		LinkedList<String> procedure = new LinkedList<String>();
		Scanner in = new Scanner(procedureLine);
		while(in.hasNext()) { procedure.add(in.next()); }
		in.close();
		
		// print the desired output
		
		// remove the first "procedure" string
	    procedure.removeFirst();
	    
	    // iterate while procedure is not empty
	    while(!procedure.isEmpty()) {
	    	String stringOne = procedure.removeFirst();
	    	// if the symbol is terminal, simply print the corresponding token
	    	if(terminalSymbols.contains(stringOne)) {
	    		ArrayList<String> temp = scannedInput.removeFirst();
	    		for(int i = 0; i < temp.size(); i++) {
	    			if(i != temp.size() - 1) { System.out.print(temp.get(i) + " "); }
	    			else { System.out.print(temp.get(i)); }
	    		}
	    		System.out.println();
	    	}
	    	// if non-terminal, keep printing the derivation rule
	    	else {
	    		helper(derivation);
	    	    }
	        }
		
		System.out.println("EOF EOF");
	}
	
	private static void helper(LinkedList<String> derivation) {
		String stringTwo = derivation.removeFirst();
		System.out.println(stringTwo);
		Scanner parse = new Scanner(stringTwo);
		ArrayList<String> derivationRule = new ArrayList<String>();
		while(parse.hasNext()) {
			derivationRule.add(parse.next());
		}
		parse.close();
		for(int i = 1; i < derivationRule.size(); i++) {
			if(terminalSymbols.contains(derivationRule.get(i))) {
				ArrayList<String> temp = scannedInput.removeFirst();
	    		for(int j = 0; j < temp.size(); j++) {
	    			if(j != temp.size() - 1) { System.out.print(temp.get(j) + " "); }
	    			else { System.out.print(temp.get(j)); }
	    		}
	    		System.out.println();		
			}
			else {
				helper(derivation);
		    }
		  }
	}
	
	private static void readInput() {
		Scanner in = new Scanner(System.in);
		while(in.hasNextLine()) {
			Scanner line = new Scanner(in.nextLine());
			ArrayList<String> temp = new ArrayList<String>();
			while(line.hasNext()) {
				temp.add(line.next());
			}
			scannedInput.add(temp);
			line.close();
		}
		in.close();
	}
	
	private static void skipGrammarAndInitialize(Scanner rule) {
	    assert(rule.hasNextInt());

	    // read the number of terminals and move to the next line
	    int numTerm = rule.nextInt();
	    rule.nextLine();

	    // skip the lines containing the terminals
	    for (int i = 0; i < numTerm; i++) {
	      terminalSymbols.add(rule.nextLine());
	    }

	    // read the number of non-terminals and move to the next line
	    int numNonTerm = rule.nextInt();
	    rule.nextLine();

	    // skip the lines containing the non-terminals
	    for (int i = 0; i < numNonTerm; i++) {
	      rule.nextLine();
	    }

	    // skip the line containing the start symbol
	    start = rule.nextLine();

	    // read the number of rules and move to the next line
	    int numRules = rule.nextInt();
	    rule.nextLine();

	    // skip the lines containing the production rules
	    for (int i = 0; i < numRules; i++) {
	      Scanner rules = new Scanner(rule.nextLine());
	      ArrayList<String> temp = new ArrayList<String>();
	      while(rules.hasNext()) {
	    	temp.add(rules.next());
	      }
	      productionRules.add(temp);
	      rules.close();
	    }
	    // read the number of states and move to the next line
	    int numStates = rule.nextInt();
	    rule.nextLine();
	    for(int i = 0; i < numStates; i++) {
	    	ArrayList<ArrayList<String>> newEntry = new ArrayList<ArrayList<String>>();
    		dfa.put(i,newEntry);
	    }
	    
	    // read the number of rules specified in DFA
	    int numOfRulesOfDfa = rule.nextInt();
	    rule.nextLine();
	    
	    for(int i = 0; i < numOfRulesOfDfa; i++) {
	    	Scanner rules = new Scanner(rule.nextLine());
	    	int numOfState = rules.nextInt();
	    	ArrayList<String> temp = new ArrayList<String>();
	    	while(rules.hasNext()) {
	    		temp.add(rules.next());
	    	}
	    	dfa.get(numOfState).add(temp);
	    	rules.close();
	    }
	    
	    // read the line(s) waited to be parsed
	    parsedLine.add("BOF");
	    for(ArrayList<String> iterator: scannedInput) {
	    	parsedLine.add(iterator.get(0));
	    }
	    parsedLine.add("EOF");
	    length = parsedLine.size();
    }
	
	private static int checkForReduce(String symbol, ArrayList<ArrayList<String>> transition, int num) {
		for(ArrayList<String> iterator : transition) {
			if(iterator.get(0).equals(symbol) && iterator.get(1).equals("reduce")) {
				num = Integer.parseInt(iterator.get(2));
				return num;
			}
		}
		return -1;
	}
	
	private static int checkForTransition(String symbol, ArrayList<ArrayList<String>> transition, int state) {
		for(ArrayList<String> iterator : transition) {
			if(iterator.get(0).equals(symbol)) {
				state = Integer.parseInt(iterator.get(2));
				return state;
			}
		}
		return -1;
	}
	
	private static void makeCfg() {
		for(ArrayList<String> iterator : result) {
			for(int i = 0; i < iterator.size(); i++) {
				if(i != iterator.size() - 1) { reverseRightMost = reverseRightMost + iterator.get(i) + " "; }
				else { reverseRightMost += iterator.get(i); }
			}
			reverseRightMost += "\n";
		}
		ArrayList<String> list = new ArrayList<String>(symStack);
		reverseRightMost = reverseRightMost + start + " ";

		for(int i = 0; i < list.size(); i++) {
			if(i != list.size() - 1) { reverseRightMost = reverseRightMost + list.get(i) + " "; }
			else { reverseRightMost += list.get(i); }
		}
		reverseRightMost += "\n";
	}
	
	private static boolean parse(){
	   stateStack.push(0);
	   while(!parsedLine.isEmpty()){
		   String symbol = parsedLine.removeFirst();
		   while(true) {
			   int numOfRule = -1;
			   numOfRule = checkForReduce(symbol,dfa.get(stateStack.peek()),numOfRule);
			   if(numOfRule < 0) {
				   break;
			   }
			   ArrayList<String> rule = productionRules.get(numOfRule);
			   result.add(rule);
			   for(int i = 0; i < rule.size() - 1; i++) {
				   symStack.pop();
				   stateStack.pop();
			   }
			   symStack.push(rule.get(0));
			   int state = -1;
			   state = checkForTransition(symStack.peek(),dfa.get(stateStack.peek()),state); 
			   if(state < 0) {
				   System.err.println("ERROR at " + (length - parsedLine.size() - 1));
				   return false;
			   }
			   stateStack.push(state);
		   }
		   symStack.push(symbol);
		   int state = -1;
		   state = checkForTransition(symbol,dfa.get(stateStack.peek()),state); 
		   if(state < 0) {
			   System.err.println("ERROR at " + (length - parsedLine.size() - 1));
			   return false;
		   }
		   stateStack.push(state);
	   }
	   makeCfg();
	   return true;
	}	
}


class CFG {
	 Scanner in;
	 private static LinkedList<String> derivation = new LinkedList<String>(); 
	 CFG(String input) {
		 in = new Scanner(input);
	 }
	 
	 private class Tree {
	     String rule;
	     LinkedList<Tree> children = new LinkedList<Tree>();
	 }
	 Set<String> terms = new HashSet<String>(); // terminals
	 Set<String> nonterms = new HashSet<String>(); // nonterminals
	 Set<String> prods = new HashSet<String>(); // production rules
	 String start; // start symbol

	 public LinkedList<String> getDerivation() {
		 return derivation;
	 }
	 // read n symbols into set t
	 public void readsyms(int n, Set<String> t) {
	     for(int i = 0; i < n; i++) {
	         t.add(in.nextLine());
	     }
	 }

	 // read single line containing integer
	 public int readln() {
	     return Integer.parseInt(in.nextLine());
	 }

	 // output leftmost derivation of tree t with indentation d
	 public void traverse(Tree t, int d) {
	     derivation.add(t.rule);
	     for(Tree c : t.children) {  // print all subtrees
	         traverse(c, d+1);
	     }
	 }

	 // print elements of set h in .cfg file format
	 public void dump(Set<String> h) {
	     System.out.println(h.size());
	     for(String s : h) {
	         System.out.println(s);
	     }
	 }

	 // pop rhs and accumulate subtress, push new tree node
	 public void popper(Stack<Tree> stack, List<String> rhs, String rule) {
	     Tree n = new Tree();
	     n.rule = rule;
	     for(String s : rhs) {
	         n.children.addFirst(stack.pop());
	     }
	     stack.push(n);
	 }

	 // build tree from remaining input
	 public Tree lrdo() {
	     Stack<Tree> stack = new Stack<Tree>();
	     String l; // lhs symbol
	     do {
	         String f = in.nextLine();
	         List<String> r = new ArrayList<String>(); // rhs symbols

	         Scanner sc = new Scanner(f);
	         l = sc.next(); // lhs symbol
	         while(sc.hasNext()) {
	             String s = sc.next();
	             if(nonterms.contains(s)) r.add(s); // only non-terminals
	         }
	         popper(stack, r, f); // reduce rule
	     } while(!start.equals(l));
	     return stack.peek();
	 }

	 public void go() {
	     readsyms(readln(), terms); // read terminals
	     readsyms(readln(), nonterms); // read nonterminals
	     start = in.nextLine(); // read start symbol
	     readsyms(readln(), prods); // read production rules
	     Tree parsetree = lrdo(); // read reverse rightmost derivation
	     traverse(parsetree, 0); // write forward leftmost derivation
	 }
	}


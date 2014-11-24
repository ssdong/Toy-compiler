/*  CS241 Scanner

    Starter code for the CS 241 assembler (assignments 3 and 4).
    Code contained here may be included in submissions for CS241
    assignments at the University of Waterloo.

    ---------------------------------------------------------------

    To compile on a CSCF linux machine, use:

            g++ -g asm.cc -o asm

    To run:
            ./asm           < source.asm > program.mips
            valgrind ./asm  < source.asm > program.mips
 */

#include <string>
#include <vector>
#include <map>
#include <iostream>
#include <cstdio>
using namespace std;

//======================================================================
//========= Declarations for the scan() function =======================
//======================================================================

// Each token has one of the following kinds.

enum Kind {
    ID,                 // Opcode or identifier (use of a label)
    INT,                // Decimal integer
    HEXINT,             // Hexadecimal integer
    REGISTER,           // Register number
    COMMA,              // Comma
    LPAREN,             // (
    RPAREN,             // )
    LABEL,              // Declaration of a label (with a colon)
    DOTWORD,            // .word directive
    WHITESPACE,         // Whitespace
    NUL                 // Bad/invalid token
};

// kindString(k) returns string a representation of kind k
// that is useful for error and debugging messages.
string kindString(Kind k);

// Each token is described by its kind and its lexeme.

struct Token {
    Kind      kind;
    string    lexeme;
    /* toInt() returns an integer representation of the token. For tokens
     * of kind INT (decimal integer constant) and HEXINT (hexadecimal integer
     * constant), returns the integer constant. For tokens of kind
     * REGISTER, returns the register number.
     */
    int       toInt();
};

// scan() separates an input line into a vector of Tokens.
vector<Token> scan(string input);

// =====================================================================
// The implementation of scan() and associated type definitions.
// If you just want to use the scanner, skip to the next ==== separator.

// States for the finite-state automaton that comprises the scanner.

enum State {
    ST_NUL,
    ST_START,
    ST_DOLLAR,
    ST_MINUS,
    ST_REGISTER,
    ST_INT,
    ST_ID,
    ST_LABEL,
    ST_COMMA,
    ST_LPAREN,
    ST_RPAREN,
    ST_ZERO,
    ST_ZEROX,
    ST_HEXINT,
    ST_COMMENT,
    ST_DOT,
    ST_DOTW,
    ST_DOTWO,
    ST_DOTWOR,
    ST_DOTWORD,
    ST_WHITESPACE
};

// The *kind* of token (see previous enum declaration)
// represented by each state; states that don't represent
// a token have stateKinds == NUL.

Kind stateKinds[] = {
    NUL,            // ST_NUL
    NUL,            // ST_START
    NUL,            // ST_DOLLAR
    NUL,            // ST_MINUS
    REGISTER,       // ST_REGISTER
    INT,            // ST_INT
    ID,             // ST_ID
    LABEL,          // ST_LABEL
    COMMA,          // ST_COMMA
    LPAREN,         // ST_LPAREN
    RPAREN,         // ST_RPAREN
    INT,            // ST_ZERO
    NUL,            // ST_ZEROX
    HEXINT,         // ST_HEXINT
    WHITESPACE,     // ST_COMMENT
    NUL,            // ST_DOT
    NUL,            // ST_DOTW
    NUL,            // ST_DOTWO
    NUL,            // ST_DOTWOR
    DOTWORD,        // ST_DOTWORD
    WHITESPACE      // ST_WHITESPACE
};

State delta[ST_WHITESPACE+1][256];

#define whitespace "\t\n\r "
#define letters    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
#define digits     "0123456789"
#define hexDigits  "0123456789ABCDEFabcdef"
#define oneToNine  "123456789"

void setT(State from, string chars, State to) {
    for(int i = 0; i < chars.length(); i++ ) delta[from][chars[i]] = to;
}

void initT(){
    int i, j;

    // The default transition is ST_NUL (i.e., no transition
    // defined for this char).
    for ( i=0; i<=ST_WHITESPACE; i++ ) {
        for ( j=0; j<256; j++ ) {
            delta[i][j] = ST_NUL;
        }
    }
    // Non-null transitions of the finite state machine.
    // NB: in the third line below, letters digits are macros
    // that are replaced by string literals, which the compiler
    // will concatenate into a single string literal.
    setT( ST_START,      whitespace,     ST_WHITESPACE );
    setT( ST_WHITESPACE, whitespace,     ST_WHITESPACE );
    setT( ST_START,      letters,        ST_ID         );
    setT( ST_ID,         letters digits, ST_ID         );
    setT( ST_START,      oneToNine,      ST_INT        );
    setT( ST_INT,        digits,         ST_INT        );
    setT( ST_START,      "-",            ST_MINUS      );
    setT( ST_MINUS,      digits,      	 ST_INT        );
    setT( ST_START,      ",",            ST_COMMA      );
    setT( ST_START,      "(",            ST_LPAREN     );
    setT( ST_START,      ")",            ST_RPAREN     );
    setT( ST_START,      "$",            ST_DOLLAR     );
    setT( ST_DOLLAR,     digits,         ST_REGISTER   );
    setT( ST_REGISTER,   digits,         ST_REGISTER   );
    setT( ST_START,      "0",            ST_ZERO       );
    setT( ST_ZERO,       "x",            ST_ZEROX      );
    setT( ST_ZERO,       digits,      	 ST_INT        );
    setT( ST_ZEROX,      hexDigits,      ST_HEXINT     );
    setT( ST_HEXINT,     hexDigits,      ST_HEXINT     );
    setT( ST_ID,         ":",            ST_LABEL      );
    setT( ST_START,      ";",            ST_COMMENT    );
    setT( ST_START,      ".",            ST_DOT        );
    setT( ST_DOT,        "w",            ST_DOTW       );
    setT( ST_DOTW,       "o",            ST_DOTWO      );
    setT( ST_DOTWO,      "r",            ST_DOTWOR     );
    setT( ST_DOTWOR,     "d",            ST_DOTWORD    );

    for ( j=0; j<256; j++ ) delta[ST_COMMENT][j] = ST_COMMENT;
}

static int initT_done = 0;

vector<Token> scan(string input){
    // Initialize the transition table when called for the first time.
    if(!initT_done) {
        initT();
        initT_done = 1;
    }

    vector<Token> ret;

    int i = 0;
    int startIndex = 0;
    State state = ST_START;

    if(input.length() > 0) {
        while(true) {
            State nextState = ST_NUL;
            if(i < input.length())
                nextState = delta[state][(unsigned char) input[i]];
            if(nextState == ST_NUL) {
                // no more transitions possible
                if(stateKinds[state] == NUL) {
                    throw("ERROR in lexing after reading " + input.substr(0, i));
                }
                if(stateKinds[state] != WHITESPACE) {
                    Token token;
                    token.kind = stateKinds[state];
                    token.lexeme = input.substr(startIndex, i-startIndex);
                    ret.push_back(token);
                }
                startIndex = i;
                state = ST_START;
                if(i >= input.length()) break;
            } else {
                state = nextState;
                i++;
            }
        }
    }

    return ret;
}

int Token::toInt() {
    if(kind == INT) {
        long long l;
        sscanf( lexeme.c_str(), "%lld", &l );
	if (lexeme.substr(0,1) == "-") {
            if(l < -2147483648LL)
                throw("ERROR: constant out of range: "+lexeme);
	} else {
	    unsigned long long ul = l;
            if(ul > 4294967295LL)
                throw("ERROR: constant out of range: "+lexeme);
	}
        return l;
    } else if(kind == HEXINT) {
        long long l;
        sscanf( lexeme.c_str(), "%llx", &l );
	unsigned long long ul = l;
        if(ul > 0xffffffffLL)
            throw("ERROR: constant out of range: "+lexeme);
        return l;
    } else if(kind == REGISTER) {
        long long l;
        sscanf( lexeme.c_str()+1, "%lld", &l );
	unsigned long long ul = l;
        if(ul > 31)
            throw("ERROR: constant out of range: "+lexeme);
        return l;
    }
    throw("ERROR: attempt to convert non-integer token "+lexeme+" to Int");
}

// kindString maps each kind to a string for use in error messages.

string kS[] = {
    "ID",           // ID
    "INT",          // INT
    "HEXINT",       // HEXINT
    "REGISTER",     // REGISTER
    "COMMA",        // COMMA
    "LPAREN",       // LPAREN
    "RPAREN",       // RPAREN
    "LABEL",        // LABEL
    "DOTWORD",      // DOTWORD
    "WHITESPACE",   // WHITESPACE
    "NUL"           // NUL
};

string kindString( Kind k ){
    if ( k < ID || k > NUL ) return "INVALID";
    return kS[k];
}

void output(int integer) {
	putchar(integer >> 24);
	putchar(integer >> 16);
	putchar(integer >> 8);
	putchar(integer);
}

int assembleReg(int s, int t, int d, int op, int funct) {
	return (op << 26) | (s << 21) | (t << 16) | (d << 11) | funct;
}

int assembleOff(int s, int t, int i, int op) {
	if(i < 0) {
		return  (op << 26) | (s << 21) | (t << 16) | (i & 0x0000ffff);
	}
	return (op << 26) | (s << 21) | (t << 16) | i;
}
//======================================================================
//======= A sample program demonstrating the use of the scanner. =======
//======================================================================

int main() {
    try {
        vector<string> srcLines;

        // Read the entire input file, storing each line as a
        // single string in the array srcLines.
        while(true) {
            string line;
            getline(cin, line);
            if(cin.fail()) break;
			if(line == "") {
				continue;
			}
            srcLines.push_back(line);
        }

        // Tokenize each line, storing the results in tokLines.
		vector<vector<Token> > tokLines;
		// word with a symbol, need to relocate
		vector<int> reloc;
		// symbol table
		map<string,int> label;
        for(int line = 0; line < srcLines.size(); line++) {
            tokLines.push_back(scan(srcLines[line]));
        }
		// PC should start at 12
		int addressCounter = 12;
		for(int line = 0 ; line < tokLines.size(); line++) {
			for(int j = 0; j < tokLines[line].size(); j++) {
				if(tokLines[line][j].kind == LABEL) {
					if(label.find(tokLines[line][j].lexeme) == label.end()) {
					    label[tokLines[line][j].lexeme] = addressCounter;
						tokLines[line].erase(tokLines[line].begin());
						--j;
					}
					else {
						throw(string("ERROR: Duplicate label"));
					}
				}
				else if(tokLines[line][j].kind != LABEL) {
					addressCounter += 4;
					break;
				}
			}
		}
		addressCounter = 12;
        // This loop checks if there is any error and at relocation labels
        for(int line=0; line < tokLines.size(); line++,addressCounter+=4 ) {
            for(int j=0; j < tokLines[line].size(); j++ ) {
                Token token = tokLines[line][j];
				if(tokLines[line][j].kind == DOTWORD){
					if(tokLines[line].size() == 2) {
						if((tokLines[line][j+1].kind == INT) || (tokLines[line][j+1].kind == HEXINT)){
						   break;
					    }
						else if (tokLines[line][j+1].kind == ID) {
							map<string,int>::iterator it = label.find(tokLines[line][j+1].lexeme + ":");
					        if(it == label.end()) {
						       throw(string("ERROR: Label ") + tokLines[line][j+1].lexeme + string("is not valid"));
					        }
					        else {
							   reloc.push_back(addressCounter);
							   break;
						    }
						}
						else {
							throw(string("ERROR: Parse error in line: .word ") + tokLines[line][j+1].lexeme);
						}
					}
					else {
						throw(string("ERROR: Parse error in line: .word"));
					}
				}
				else if(tokLines[line][j].kind == ID) {
					if(tokLines[line][j].lexeme == "jr") {
						if(tokLines[line].size() == 2) {
							if(tokLines[line][j+1].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: jr"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: jr"));
						}
					}
					else if(tokLines[line][j].lexeme == "jalr") {
						if(tokLines[line].size() == 2) {
							if(tokLines[line][j+1].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: jr"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: jr"));
						}
					}
					else if(tokLines[line][j].lexeme == "add") {
						if(tokLines[line].size() == 6) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   tokLines[line][j+3].kind == REGISTER && tokLines[line][j+4].kind == COMMA &&
							   tokLines[line][j+5].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: add"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: add"));
						}
					}
					else if(tokLines[line][j].lexeme == "sub") {
						if(tokLines[line].size() == 6) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   tokLines[line][j+3].kind == REGISTER && tokLines[line][j+4].kind == COMMA &&
							   tokLines[line][j+5].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: sub"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: sub"));
						}
					}
					else if(tokLines[line][j].lexeme == "slt") {
						if(tokLines[line].size() == 6) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   tokLines[line][j+3].kind == REGISTER && tokLines[line][j+4].kind == COMMA &&
							   tokLines[line][j+5].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: slt"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: slt"));
						}
					}
					else if(tokLines[line][j].lexeme == "sltu") {
						if(tokLines[line].size() == 6) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   tokLines[line][j+3].kind == REGISTER && tokLines[line][j+4].kind == COMMA &&
							   tokLines[line][j+5].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: sltu"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: sltu"));
						}
					}
					else if(tokLines[line][j].lexeme == "bne" || tokLines[line][j].lexeme == "beq") {
						 if(tokLines[line].size() == 6) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   tokLines[line][j+3].kind == REGISTER && tokLines[line][j+4].kind == COMMA &&
							   (tokLines[line][j+5].kind == INT || tokLines[line][j+5].kind == HEXINT || 
							    tokLines[line][j+5].kind == ID)) {
								tokLines[line][j+1].toInt();
								tokLines[line][j+3].toInt();
								int temp;
								if(tokLines[line][j+5].kind == INT) {
									temp = tokLines[line][j+5].toInt();
									if(temp < -32768 || temp > 32767) {
										throw(string("ERROR: constant out of range"));
									}
								}
								else if(tokLines[line][j+5].kind == HEXINT) {
									temp = tokLines[line][j+5].toInt();
									if(temp < 0 || temp > 65535) {
										throw(string("ERROR: constant out of range"));
									}
								}
								else {
									map<string,int>::iterator it = label.find(tokLines[line][j+5].lexeme + ":");
					                if(it == label.end()) {
						                throw(string("ERROR: Label ") + tokLines[line][j+5].lexeme + string(" is not valid"));
					                }
					                else {
										temp = it->second;
										int offset = (temp - (addressCounter + 4)) / 4;
										if(offset < -32768 || offset > 32767) {
											throw(string("ERROR: constant out of range"));
										}
						            }
								}
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
						}
					}
					else if(tokLines[line][j].lexeme == "lis") {
						if(tokLines[line].size() == 2) {
							if(tokLines[line][j+1].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: lis"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: lis"));
						}
					}
					else if(tokLines[line][j].lexeme == "mflo") {
						if(tokLines[line].size() == 2) {
							if(tokLines[line][j+1].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: mflo"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: mflo"));
						}
					}
					else if(tokLines[line][j].lexeme == "mfhi") {
						if(tokLines[line].size() == 2) {
							if(tokLines[line][j+1].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: mfhi"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: mfhi"));
						}
					}
					else if(tokLines[line][j].lexeme == "mult") {
						if(tokLines[line].size() == 4) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+3].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: mult"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: mult"));
						}
					}
					else if(tokLines[line][j].lexeme == "multu") {
						if(tokLines[line].size() == 4) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+3].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: multu"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: multu"));
						}
					}
					else if(tokLines[line][j].lexeme == "div") {
						if(tokLines[line].size() == 4) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+3].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: div"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: div"));
						}
					}
					else if(tokLines[line][j].lexeme == "divu") {
						if(tokLines[line].size() == 4) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+3].kind == REGISTER) {
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: divu"));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: divu"));
						}
					}
					else if(tokLines[line][j].lexeme == "lw" || tokLines[line][j].lexeme == "sw") {
						if(tokLines[line].size() == 7) {
							if(tokLines[line][j+1].kind == REGISTER && tokLines[line][j+2].kind == COMMA &&
							   (tokLines[line][j+3].kind == INT || tokLines[line][j+3].kind == HEXINT)   && 
							   tokLines[line][j+4].kind == LPAREN && tokLines[line][j+5].kind == REGISTER &&
							   tokLines[line][j+6].kind == RPAREN) {
								tokLines[line][j+1].toInt();
								tokLines[line][j+5].toInt();
								int temp;
								if(tokLines[line][j+3].kind == INT) {
								   temp = tokLines[line][j+3].toInt();
								   if(temp < -32768 || temp > 32767) {
									  throw(string("ERROR: constant out of range"));
								   }
								}
								else {
									temp = tokLines[line][j+3].toInt();
									if(temp < 0 || temp > 65535) {
										throw(string("ERROR: constant out of range"));
									}
								}
								break;
							}
							else {
								throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
							}
						}
						else {
							throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
						}
					}
					else {
						throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
					}
				}
				else {
					throw(string("ERROR: Parse error in line: " + tokLines[line][j].lexeme));
				}
			}
			if(tokLines[line].empty()) {
				addressCounter -= 4;
			}
        }

		int endCode = addressCounter;
		int endModule = endCode + reloc.size() * 2 * 4;

		output(268435458);
		output(endModule);
		output(endCode);
		// The loop output the result 

		addressCounter = 12;
		for(int line=0; line < tokLines.size(); line++,addressCounter += 4) {
            for(int j=0; j < tokLines[line].size(); j++ ) {
                Token token = tokLines[line][j];
				if(tokLines[line][j].kind == DOTWORD){
						if((tokLines[line][j+1].kind == INT) || (tokLines[line][j+1].kind == HEXINT)){
						   output(tokLines[line][j+1].toInt());
						   break;
					    }
						map<string,int>::iterator it = label.find(tokLines[line][j+1].lexeme + ":");
				        output(it->second);
						break;
				}
				else if(tokLines[line][j].kind == ID) {
					if(tokLines[line][j].lexeme == "jr") {
						  int registerNum = tokLines[line][j+1].toInt();
						  int binaryCode = assembleReg(registerNum,0,0,0,8);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "jalr") {
						  int registerNum = tokLines[line][j+1].toInt();
						  int binaryCode = assembleReg(registerNum,0,0,0,9);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "add") {
						  int d = tokLines[line][j+1].toInt();
						  int s = tokLines[line][j+3].toInt();
					   	  int t = tokLines[line][j+5].toInt();
						  int binaryCode = assembleReg(s,t,d,0,32);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "sub") {
			              int d = tokLines[line][j+1].toInt();
						  int s = tokLines[line][j+3].toInt();
						  int t = tokLines[line][j+5].toInt();
						  int binaryCode = assembleReg(s,t,d,0,34);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "slt") {
						  int d = tokLines[line][j+1].toInt();
						  int s = tokLines[line][j+3].toInt();
						  int t = tokLines[line][j+5].toInt();
						  int binaryCode = assembleReg(s,t,d,0,42);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "sltu") {
						  int d = tokLines[line][j+1].toInt();
						  int s = tokLines[line][j+3].toInt();
						  int t = tokLines[line][j+5].toInt();
						  int binaryCode = assembleReg(s,t,d,0,43);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "beq" || 
						    tokLines[line][j].lexeme == "bne") {
						  int s = tokLines[line][j+1].toInt();
						  int t = tokLines[line][j+3].toInt();
						  int i;
						  int offset;
						  int binaryCode;
						  if(tokLines[line][j+5].kind == ID){
							 map<string,int>::iterator it = label.find(tokLines[line][j+5].lexeme + ":");
							 i = it->second;
							 offset = (i - (addressCounter + 4)) / 4;
						  }
						  else {
						     i = tokLines[line][j+5].toInt();
							 offset = i;
						  }
						  if(tokLines[line][j].lexeme == "beq") {
						     binaryCode = assembleOff(s,t,offset,4);
						  }
						  else {
							 binaryCode = assembleOff(s,t,offset,5);
						  }
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "lis") {
						  int d = tokLines[line][j+1].toInt();
						  int binaryCode = assembleReg(0,0,d,0,20);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "mflo") {
						  int d = tokLines[line][j+1].toInt();
						  int binaryCode = assembleReg(0,0,d,0,18);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "mfhi") {
						  int d = tokLines[line][j+1].toInt();
						  int binaryCode = assembleReg(0,0,d,0,16);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "mult") {
						  int s = tokLines[line][j+1].toInt();
						  int t = tokLines[line][j+3].toInt();
						  int binaryCode = assembleReg(s,t,0,0,24);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "multu") {
						  int s = tokLines[line][j+1].toInt();
						  int t = tokLines[line][j+3].toInt();
						  int binaryCode = assembleReg(s,t,0,0,25);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "div") {
						  int s = tokLines[line][j+1].toInt();
						  int t = tokLines[line][j+3].toInt();
						  int binaryCode = assembleReg(s,t,0,0,26);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "divu") {
						  int s = tokLines[line][j+1].toInt();
						  int t = tokLines[line][j+3].toInt();
						  int binaryCode = assembleReg(s,t,0,0,27);
						  output(binaryCode);
						  break;
					}
					else if(tokLines[line][j].lexeme == "lw" || tokLines[line][j].lexeme == "sw") {
						  int t = tokLines[line][j+1].toInt();
						  int s = tokLines[line][j+5].toInt();
						  int i = tokLines[line][j+3].toInt();
						  int binaryCode;
						  if(tokLines[line][j].lexeme == "lw") {
						     binaryCode = assembleOff(s,t,i,35);
						  }
						  else {
							 binaryCode = assembleOff(s,t,i,43);
						  }
						  output(binaryCode);
						  break;
					}
					else {
					}
				}
				else {
				}
			}
			if(tokLines[line].empty()) {
				addressCounter -= 4;
			}
        }
		for(vector<int>::iterator it = reloc.begin(); it != reloc.end(); it++) {
                 output(1);
				 output(*it);
		}
    } catch(string msg) {
        cerr << msg << endl;
		return 1;
    }
    return 0;
}
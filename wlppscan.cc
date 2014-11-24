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
#include <iostream>
#include <cstdio>
using namespace std;

//======================================================================
//========= Declarations for the scan() function =======================
//======================================================================

// Each token has one of the following kinds.

enum Kind {
    ID,                 // A string consisting of a letter followed by zero or more letters and digits
    ZERO,               // 0
    NUM,                // integer
    LPAREN,             // (
    RPAREN,             // )
    LBRACE,             // {
    RBRACE,             // }
    INT,                // type int
    BECOMES,            // "="
    BANG,               // "!"
    EQ,                 // "=="
    NE,                 // "!="
    LT,                 // "<"
    GT,                 // ">"
    LE,                 // "<="
    GE,                 // ">="
    PLUS,               // "+"
    MINUS,              // "-"
    STAR,               // "*"
    SLASH,              // "/"
    PCT,                // "%"
    COMMA,              // ","
    SEMI,               // ";"
    LBRACK,             // "["
    RBRACK,             // "]"
    AMP,                // "&"
    NUL,                // "NULL"
    TAB,                // \t
    NEWLINE,            // \n
    COMMENT,            // //
    WHITESPACE,         // \s
    INVALID,            //  
};

// kindString(k) returns string a representation of kind k
// that is useful for error and debugging messages.
string kindString(Kind k);

// Each token is described by its kind and its lexeme.

struct Token {
    Kind      kind;
    string    lexeme;
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
    ST_ZERO,
    ST_ID,
    ST_NUM,
    ST_LPAREN,
    ST_RPAREN,
    ST_LBRACE,
    ST_RBRACE,
    ST_BECOMES,
    ST_BANG,
    ST_INT,
    ST_EQ,
    ST_NE,
    ST_LT,
    ST_GT,
    ST_LE,
    ST_GE,
    ST_PLUS,
    ST_MINUS,
    ST_STAR,
    ST_SLASH,
    ST_PCT,
    ST_COMMA,
    ST_SEMI,
    ST_LBRACK,
    ST_RBRACK,
    ST_AMP,
    ST_INVALID,
    ST_COMMENT,
    ST_WHITESPACE,
};

// The *kind* of token (see previous enum declaration)
// represented by each state; states that don't represent
// a token have stateKinds == NUL.

Kind stateKinds[] = {
    NUL,       //ST_NUL,
    NUL,       //ST_START,
    ZERO,      //ST_ZERO,
    ID,        //ST_ID,
    NUM,       //ST_NUM,
    LPAREN,    //ST_LPAREN,
    RPAREN,    //ST_RPAREN,
    LBRACE,    //ST_LBRACE,
    RBRACE,    //ST_RBRACE,
    BECOMES,   //ST_BECOMES,
    BANG,      //ST_BANG,
    INT,       //ST_INT,
    EQ,        //ST_EQ,
    NE,        //ST_NE,
    LT,        //ST_LT,
    GT,        //ST_GT,
    LE,        //ST_LE,
    GE,        //ST_GE,
    PLUS,      //ST_PLUS,
    MINUS,       //ST_MINUS,
    STAR,      //ST_STAR,
    SLASH,     //ST_SLASH,
    PCT,       //ST_PCT,
    COMMA,     //ST_COMMA,
    SEMI,      //ST_SEMI,
    LBRACK,    //ST_LBRACK,
    RBRACK,    //ST_RBRACK,
    AMP,       //ST_AMP,
    INVALID,   //ST_INVALID,
    COMMENT,   //ST_COMMENT,
    WHITESPACE,//ST_WHITESPACE,
};

State delta[ST_WHITESPACE+1][256];

#define whitespace "\t\n\r "
#define letters    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
#define digits     "0123456789"
#define oneToNine  "123456789"
#define opcode     "=!<>+-*/%,;&{}()[]"
#define lettersAndDigits "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
#define everythingWithoutEQ " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!<>+-*/%,;&{}()[]"

void setT(State from, string chars, State to) {
    for(int i = 0; i < chars.length(); i++ ) delta[from][chars[i]] = to;
}

void initT(){
    int i, j;

    // The default transition is ST_NUL (i.e., no transition
    // defined for this char).
    for ( i=0; i <= ST_WHITESPACE; i++ ) {
        for ( j=0; j < 256; j++ ) {
            delta[i][j] = ST_NUL;
        }
    }
    // Non-null transitions of the finite state machine.
    // NB: in the third line below, letters digits are macros
    // that are replaced by string literals, which the compiler
    // will concatenate into a single string literal.
    setT( ST_START,      whitespace,     ST_WHITESPACE );
    setT( ST_START,      "0",            ST_ZERO       );
    setT( ST_START,      oneToNine,      ST_NUM        );
    setT( ST_START,      letters,        ST_ID         );
    setT( ST_START,      "=",            ST_BECOMES    );
    setT( ST_START,      "!",            ST_BANG       );
    setT( ST_START,      "<",            ST_LT         );
    setT( ST_START,      ">",            ST_GT         );
    setT( ST_START,      "+",            ST_PLUS       );
    setT( ST_START,      "-",            ST_MINUS      );
    setT( ST_START,      "*",            ST_STAR       );
    setT( ST_START,      "/",            ST_SLASH      );
    setT( ST_START,      "%",            ST_PCT        );
    setT( ST_START,      ",",            ST_COMMA      );
    setT( ST_START,      ";",            ST_SEMI       );
    setT( ST_START,      "&",            ST_AMP        );
    setT( ST_START,      "{",            ST_LBRACE     );
    setT( ST_START,      "}",            ST_RBRACE     );
    setT( ST_START,      "(",            ST_LPAREN     );
    setT( ST_START,      ")",            ST_RPAREN     );
    setT( ST_START,      "[",            ST_LBRACK     );
    setT( ST_START,      "]",            ST_RBRACK     );
    setT( ST_SLASH,      "/",            ST_COMMENT    );
    setT( ST_ZERO,      lettersAndDigits,ST_INVALID    );
    setT( ST_NUM,       digits,          ST_NUM        );
    setT( ST_NUM,       letters,         ST_INVALID    );
    setT( ST_ID,        lettersAndDigits,ST_ID         );
    setT( ST_BECOMES,   "=",             ST_EQ         );
    setT( ST_BANG,      "=",             ST_NE         );
    setT( ST_BANG,      everythingWithoutEQ, ST_INVALID);
    setT( ST_LT,        "=",             ST_LE         );
    setT( ST_GT,        "=",             ST_GE         );
    setT( ST_WHITESPACE, whitespace,     ST_WHITESPACE );
    for ( j=0; j<256; j++ ) delta[ST_COMMENT][j] = ST_COMMENT;
}

static int initT_done = 0;

vector<Token> scan(string input){
    // Initialize the transition table when called for the first time
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
            if(i < input.length()) {
                nextState = delta[state][(unsigned char) input[i]];
            }
            if(nextState == ST_INVALID) {
                throw(string("ERROR"));
            }
            else if(nextState == ST_COMMENT) {
                break;
            }
            else if(nextState == ST_NUL) {
                if(stateKinds[state] != WHITESPACE) {
                    Token token;
                    token.kind = stateKinds[state];
                    token.lexeme = input.substr(startIndex,i-startIndex);
                    ret.push_back(token);
                }
                startIndex = i;
                state = ST_START;
                if(i >= input.length()) break;
            }
            else {
                state = nextState;
                ++i;
            }
        }
    }
    return ret;
}


// kindString maps each kind to a string for use in error messages.

string kS[] = {
    "ID",                 // A string consisting of a letter followed by zero or more letters and digits
    "NUM",               // 0
    "NUM",                // integer
    "LPAREN",             // (
    "RPAREN",             // )
    "LBRACE",             // {
    "RBRACE",             // }
    "INT",                // type int
    "BECOMES",            // "="
    "BANG",               // "!"
    "EQ",                 // "=="
    "NE",                 // "!="
    "LT",                 // "<"
    "GT",                 // ">"
    "LE",                 // "<="
    "GE",                 // ">="
    "PLUS",               // "+"
    "MINUS",              // "-"
    "STAR",               // "*"
    "SLASH",              // "/"
    "PCT",                // "%"
    "COMMA",              // ","
    "SEMI",               // ";"
    "LBRACK",             // "["
    "RBRACK",             // "]"
    "AMP",                // "&"
    "NUL",                // "NULL"
    "TAB",                // \t
    "NEWLINE",            // \n
    "COMMENT",            // //
    "WHITESPACE",         // \s
    "INVALID",            //  
};

string kindString( Kind k ){
    if ( k < ID || k > INVALID ) return "INVALID";
    return kS[k];
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
            srcLines.push_back(line);
        }

        // Tokenize each line, storing the results in tokLines.
        vector<vector<Token> > tokLines;

        for(int line = 0; line < srcLines.size(); line++) {
            tokLines.push_back(scan(srcLines[line]));
        }

        // Now we process the tokens.
        // Sample usage: print the tokens of each line.
        for(int line=0; line < tokLines.size(); line++ ) {
            for(int j=0; j < tokLines[line].size(); j++ ) {
                Token token = tokLines[line][j];
                if(token.lexeme == "return") {
                    cout << "RETURN return" << endl;
                }
                else if(token.lexeme == "if") {
                    cout << "IF if" << endl;
                }
                else if(token.lexeme == "else") {
                    cout << "ELSE else" << endl;
                }
                else if(token.lexeme == "while") {
                    cout << "WHILE while" << endl;
                }
                else if(token.lexeme == "println") {
                    cout << "PRINTLN println" << endl;
                }
                else if(token.lexeme == "wain") {
                    cout << "WAIN wain" << endl;
                }
                else if(token.lexeme == "int") {
                    cout << "INT int" << endl;
                }
                else if(token.lexeme == "new") {
                    cout << "NEW new" << endl;
                }
                else if(token.lexeme == "delete") {
                    cout << "DELETE delete" << endl;
                }
                else if(token.lexeme == "NULL") {
                    cout << "NULL NULL" << endl;
                }
                else {
                    cout << kindString(token.kind) << " " << token.lexeme << endl;
                }
            }
        }
    } catch(string msg) {
        cerr << msg << endl;
    }
    return 0;
}
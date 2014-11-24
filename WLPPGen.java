import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Starter code for CS241 assignments 9-11 for Spring 2011.
 * 
 * Based on Scheme code by Gord Cormack. Java translation by Ondrej Lhotak.
 * 
 * Version 20081105.1
 *
 * Modified June 30, 2011 by Brad Lushman
 */
public class WLPPGen {
    Scanner in = new Scanner(System.in);

    // The set of terminal symbols in the WLPP grammar.
    Set<String> terminals = new HashSet<String>(Arrays.asList("BOF", "BECOMES", 
         "COMMA", "ELSE", "EOF", "EQ", "GE", "GT", "ID", "IF", "INT", "LBRACE", 
         "LE", "LPAREN", "LT", "MINUS", "NE", "NUM", "PCT", "PLUS", "PRINTLN",
         "RBRACE", "RETURN", "RPAREN", "SEMI", "SLASH", "STAR", "WAIN", "WHILE",
         "AMP", "LBRACK", "RBRACK", "NEW", "DELETE", "NULL"));

    HashMap<String,String> symbolTable;
    HashMap<String,Integer> offsetTable;
    //HashSet<String> pointerTable; 
   
    int offset = -4;
    int labelCount = 0;

    // Data structure for storing the parse tree.
    private class Tree {
        List<String> rule;

        ArrayList<Tree> children = new ArrayList<Tree>();

        // Does this node's rule match otherRule?
        boolean matches(String otherRule) {
            return tokenize(otherRule).equals(rule);
        }
    }

 // Main program
    public static final void main(String args[]) {
        WLPPGen wlppGen = new WLPPGen();
        wlppGen.go();   
    }
    
    // Divide a string into a list of tokens.
    List<String> tokenize(String line) {
        List<String> ret = new ArrayList<String>();
        Scanner sc = new Scanner(line);
        while (sc.hasNext()) {
            ret.add(sc.next());
        }
        return ret;
    }

    // Read and return wlppi parse tree
    Tree readParse(String lhs) {
        String line = in.nextLine();
        List<String> tokens = tokenize(line);
        Tree ret = new Tree();
        ret.rule = tokens;
        if (!terminals.contains(lhs)) {
            Scanner sc = new Scanner(line);
            sc.next(); // discard LHS
            while (sc.hasNext()) {
                String s = sc.next();
                ret.children.add(readParse(s));
            }
        }
        return ret;
    }

    // Compute symbols defined in t
    void genSymbols(Tree t) {
    	List<String> rules = t.rule;
    	for(int i = 1; i < rules.size(); i++) {
    		String temp = rules.get(i);
    		if(terminals.contains(temp) && (!temp.equals("ID"))) {
    			   continue;
    		}
    		if(temp.equals("type")) {
    			   String id = t.children.get(i).rule.get(1);
    			   String type = t.children.get(i-1).rule.size() == 2 ? "int" : "int*";
    			   if(symbolTable.containsKey(id)) { bail("Only one declaration is allowed with " + id); }
    			   symbolTable.put(id, type);
    			   return;
    		}
    		else if(temp.equals("ID")) {
    			   String id = t.children.get(i-1).rule.get(1);	   
    			   if(!symbolTable.containsKey(id)) { bail("Identifier used before declaration " + id);}
    		       return;
    		}
    		else {
    			   genSymbols(t.children.get(i-1));
    		}
    	}
    }
 
    // Print an error message and exit the program.
    void bail(String msg) {
        System.err.println("ERROR: " + msg);
        System.exit(0);
    }

    // Generate the code for the parse tree t.
    String genCode(Tree t) {
        return null;
    }
    
    public String checkStatementType(Tree t) {
    	 List<String> rules = t.rule;
    	 for(int i = 1; i < rules.size(); i++) {
    		 String temp = rules.get(i);
    		 if(temp.equals("procedure")) {
    			 Tree procedure = t.children.get(i-1);
    			 String checkType = checkStatementType(procedure.children.get(5));
    			 if(checkType.equals("int*")) { bail("Second arg needs to be an integer");}                 
    			 checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("type")) {
    			 String type = t.children.get(i-1).rule.size() == 2 ? "int" : "int*";
    			 return type;
    		 }
    		 else if(temp.equals("dcls")) {
    			 checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("dcl")) {
    			 String nextSymbol = rules.get(i+1);
    			 if(nextSymbol.equals("BECOMES")) {
    				 String rhsType = t.rule.get(i+2).equals("NUM")? "int" : "int*";
    				 String lhsType = checkStatementType(t.children.get(i-1));
    				 if(!lhsType.equals(rhsType)) {bail("Declaration assignment type does not match");}
    				 i += 2;
    			 }
    		 }
    		 else if(temp.equals("statements")) {
    			 checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("statement")) {
    			 checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("test")) {
    			 checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("expr")) {
    			 String nextSymbol = rules.get(i+1);
    			 if(nextSymbol.equals("EQ") || nextSymbol.equals("NE") || nextSymbol.equals("LT") ||
    				nextSymbol.equals("LE") || nextSymbol.equals("GE") || nextSymbol.equals("GT")) {
    				   String lhsType = checkStatementType(t.children.get(i-1));
    				   String rhsType = checkStatementType(t.children.get(i+1));
    				   if(!lhsType.equals(rhsType)) { bail("Compare different types"); }
    				   i+=2;
    			 }
    			 else if(nextSymbol.equals("PLUS")) {
    				 String lhsType = checkStatementType(t.children.get(i-1));
  				     String rhsType = checkStatementType(t.children.get(i+1));
  				     if(lhsType.equals("int") && rhsType.equals("int")) { return "int"; }
  				     else if(lhsType.equals("int") && rhsType.equals("int*")) { return "int*"; }
  				     else if(lhsType.equals("int*") && rhsType.equals("int")){ return "int*"; }
  				     else { bail("Failed + operation"); }
  				     i+=2;
    			 }
    			 else if(nextSymbol.equals("MINUS")) {
    				 String lhsType = checkStatementType(t.children.get(i-1));
  				     String rhsType = checkStatementType(t.children.get(i+1));
  				     if(lhsType.equals("int") && rhsType.equals("int")) { return "int"; }
  				     else if(lhsType.equals("int*") && rhsType.equals("int")){ return "int*"; }
  				     else if(lhsType.equals("int*") && rhsType.equals("int*")){ return "int"; }
  				     else {bail("Failed - operation");}
  				     i+=2;
    			 }
    			 else {
    				 return checkStatementType(t.children.get(i-1));
    			 }
    		 }
    		 else if(temp.equals("term")) {
    			 if(i == rules.size() - 1) { return checkStatementType(t.children.get(i-1)); }
    			 String nextSymbol = rules.get(i+1);
    			 if(nextSymbol.equals("STAR") || nextSymbol.equals("SLASH") || nextSymbol.equals("PCT")) {
    				 String lhsType = checkStatementType(t.children.get(i-1));
    				 String rhsType = checkStatementType(t.children.get(i+1));
    				 if(lhsType.equals("int") && rhsType.equals("int")) {return "int";}
    				 else { bail("Failed * operation"); }
    			 }
    			 else {
    				 return checkStatementType(t.children.get(i-1));
    			 }
    		 }
    		 else if(temp.equals("NUM")) {
    			 return "int";
    		 }
    		 else if(temp.equals("NULL")) {
    			 return "int*";
    		 }
    		 else if(temp.equals("AMP")) {
    			 String rhsType = checkStatementType(t.children.get(i));
    			 if(rhsType.equals("int")) { return "int*"; }
    			 else { bail("Failed & operation"); }
    		 }
    		 else if(temp.equals("STAR")) {
    			 String rhsType = checkStatementType(t.children.get(i));
    			 if(rhsType.equals("int*")) { return "int"; }
    			 else { bail("Failed * operation"); }
    		 }
    		 else if(temp.equals("NEW")) {
    			 String rhsType = checkStatementType(t.children.get(i+2));
    			 if(rhsType.equals("int")) { return "int*"; }
    			 else { bail("Failed new operation"); }
    		 }
    		 else if(temp.equals("factor")) {
    			 return checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("ID")) {
    			 return symbolTable.get(t.children.get(i-1).rule.get(1));
    		 }
    		 else if(temp.equals("lvalue")) {
    			 String nextSymbol = rules.get(i+1);
    			 if(nextSymbol.equals("BECOMES")) {
    				 String lhsType = checkStatementType(t.children.get(i-1));
    				 String rhsType = checkStatementType(t.children.get(i+1)); // int
    				 if(!lhsType.equals(rhsType)) {bail("Assignment type does not match");}
    				 i+=2;
    			 }
    			 return checkStatementType(t.children.get(i-1));
    		 }
    		 else if(temp.equals("PRINTLN")) {
    			 String rhsType = checkStatementType(t.children.get(i+1));
    			 if(!rhsType.equals("int")) {bail("Can only print integer");}
    			 i += 2;
    		 }
    		 else if(temp.equals("DELETE")) {
    			 String rhsType = checkStatementType(t.children.get(i+2));
    			 if(!rhsType.equals("int*")) {bail("Can only delete pointers");}
    			 i += 3;
    		 }
    		 else if(temp.equals("RETURN")) {
    			 String rhsType = checkStatementType(t.children.get(i));
    			 if(!rhsType.equals("int")) {bail("Can only return int");}
    			 ++i;
    		 }
    		 else {
    			 continue;
    		 }
    	 }
    	 return "";
    }

    
    public String preprocess() {
    	return ".import print\n" +
               ".import init\n" +
    		   ".import new\n" +
               ".import delete\n" +
    		   "lis $4\n" +
               ".word 4\n" + 
    		   "lis $10\n" +
               ".word print\n" +
    		   "lis $20\n" +
               ".word init\n" +
    		   "lis $21\n" +
               ".word new\n" +
    		   "lis $22\n" +
               ".word delete\n" +
    		   "add $29, $30, $0\n";
    }
    
    public String endprocess() {
    	return "jr $31";
    }
    
    public String push(String register) {
    	return "sub $30, $30, $4\n" +
               "sw" + " " + register + "," + " " + "0($30)\n";  
    }
    
    public String pop(String register) {
    	return "lw" + " " + register + "," + "0($30)\n" +
               "add $30, $30, $4\n";
    }
    
    public String codeGenerator(Tree t) {
    	List<String> rule = t.rule;
    	String lhs = rule.get(0);
    	if(lhs.equals("S")) {
    		Tree procedure = t.children.get(1);
    		return preprocess() + codeGenerator(procedure);
    	}
    	else if(lhs.equals("procedure")) {
    		    int type = t.children.get(3).children.get(0).rule.size();
    		    String dcl1ID = t.children.get(3).children.get(1).rule.get(1);
    		    String dcl2ID = t.children.get(5).children.get(1).rule.get(1);
    		    offsetTable.put(dcl1ID, new Integer(offset));
    		    offset -= 4;
    		    offsetTable.put(dcl2ID, new Integer(offset));
    		    offset -= 4;
    		    Tree dcls = t.children.get(8);
    		    Tree statements = t.children.get(9);
    		    Tree expr = t.children.get(11);	
    		    if(type == 2) {
    		       return  push("$1") + 
    		    		   push("$2") +
    		    		   "add $2, $0, $0\n" +
    		    		   push("$31") + 
    		    		   "jalr $20\n" +
    		    		   pop("$31") +
    		    		   codeGenerator(dcls) + codeGenerator(statements) +
    		    		   codeGenerator(expr) + endprocess();
    		    }
    		    else {
    		       return push("$1") +
    		    		  push("$2") +
    		    		  push("$31") +
    		    		  "jalr $20\n" +
    		    		  pop("$31") +
    		    		  codeGenerator(dcls) + codeGenerator(statements) +
   		    		      codeGenerator(expr) + endprocess();
    		    }
    	}
    	else if(lhs.equals("dcls")) {
    		if(t.rule.size() == 1) {}
    		else {
    			String temp = t.rule.get(4);
    			Tree dcls = t.children.get(0);
				String executeDcls = codeGenerator(dcls);
				String identifier = t.children.get(1).children.get(1).rule.get(1);
				offsetTable.put(identifier, new Integer(offset));
				offset -= 4;
    			if(temp.equals("NUM")) {
    				String number = t.children.get(3).rule.get(1);
    				return executeDcls + 
    					   "lis $3\n" +
    				       ".word" + " " + number + "\n" +
    					   push("$3");
    			}
    			else {
    				return executeDcls +
    						"add $3, $0, $0\n" +
    						push("$3");
    			}
    		}
    	}
    	else if(lhs.equals("statements")) {
    		if(t.rule.size() == 1) {}
    		else {
    			Tree statements = t.children.get(0);
    			Tree statement = t.children.get(1);
    			return codeGenerator(statements) + 
    				   codeGenerator(statement);
    		}
    	}
    	else if(lhs.equals("lvalue")) {
    		String temp = t.rule.get(1);
    		if(temp.equals("ID")) {
    			String lexeme = t.children.get(0).rule.get(1);
    			return lexeme;
    		}
    		else {
    			Tree lvalue = t.children.get(1);
    		    String lexeme = codeGenerator(lvalue);
    		    return lexeme;
    		}
    	}
    	else if(lhs.equals("statement")) {
    		String temp = t.rule.get(1);
    		if(temp.equals("PRINTLN")) {
    			Tree expr = t.children.get(2);
    			String executeExpr = codeGenerator(expr);
    			return executeExpr +
    				   "add $1, $3, $0\n" + 
    				   "sw $31, -4($30)\n" +
    			       "sub $30, $30, $4\n" +
    				   "jalr $10\n" +
    			       "add $30, $30, $4\n" +
    				   "lw $31, -4($30)\n";
    		}
    		else if(temp.equals("lvalue")) {
    			Tree expr = t.children.get(2);
				String executeExpr = codeGenerator(expr);
    			if(t.children.get(0).rule.get(1).equals("STAR")) {
    				Tree factor = t.children.get(0).children.get(1);
    				String executeFactor = codeGenerator(factor);
    				return executeFactor+
     			    	   push("$3") +
     			    	   executeExpr +
     			           pop("$8")+
                           "sw $3, 0($8)\n";  
    			}
    			else {
    				String id = codeGenerator(t.children.get(0));
    				Integer offsetOfLvalue = offsetTable.get(id);
    			    return executeExpr +
    				       "sw $3," + " " + offsetOfLvalue +"($29)\n";  
    			}
    		}
    		else if(temp.equals("WHILE")) {
    			Tree test = t.children.get(2);
    			Tree statements = t.children.get(5);
    			String executeTest = codeGenerator(test);
    			String executeStatements = codeGenerator(statements);
    			labelCount += 1;
    			String startLabel = "start" + labelCount;
    			String endLabel = "end" + labelCount;
    			return  startLabel + ":" + "\n" + 
    					executeTest +
                       "beq $3, $0," + " " + endLabel +"\n" +
    			        executeStatements +
                       "beq $0, $0," + " " + startLabel + "\n" +
    			        endLabel + ":" + "\n";
    		}
    		else if(temp.equals("IF")) {
    			Tree test = t.children.get(2);
    			Tree lStatements = t.children.get(5);
    			Tree RStatements = t.children.get(9);
    			String executeTest = codeGenerator(test);
    			String executeLStatements = codeGenerator(lStatements);
    			String executeRStatements = codeGenerator(RStatements);
    			labelCount += 1;
    			String start = "start" + labelCount;
    			String end = "end" + labelCount;
    			return  
				    executeTest +
                    "beq $3, $0," + " " + start +"\n" +
				    executeLStatements +
				    "beq $0, $0," + " " + end + "\n" +
				    start + ":" + "\n" +
				    executeRStatements +
    			    end + ":" + "\n";
    		}
    		else {
    			Tree expr = t.children.get(3);
    			String executeExpr = codeGenerator(expr);
    			return 
    				executeExpr +
    				"add $1, $3, $0\n" +
    			    push("$31") +
    			    "jalr $22\n" +
    			    pop("$31");
    		}
    	}
    	else if(lhs.equals("test")) {
    		String op = t.rule.get(2);
    		Tree lExpr = t.children.get(0);
			Tree RExpr = t.children.get(2);
			String executeLExpr = codeGenerator(lExpr);
			String executeRExpr = codeGenerator(RExpr);
			String typeL = checkStatementType(lExpr);
			String typeR = checkStatementType(RExpr);
    		if(op.equals("LT")) {
    			return executeLExpr + 
    				   "add $5, $3, $0\n" +
    				   executeRExpr + 
    				   ((typeL.equals("int") && typeR.equals("int"))? "slt $3, $5, $3\n" : "sltu $3, $5, $3\n" );
    		}
    		else if(op.equals("GT")) {
    			return executeLExpr + 
     				   "add $5, $3, $0\n" +
     				   executeRExpr +
     				   ((typeL.equals("int") && typeR.equals("int"))? "slt $3, $3, $5\n" : "sltu $3, $3, $5\n" );
    		}
    		else if(op.equals("NE")) {
    			return executeLExpr +
    				   "add $5, $3, $0\n" +
    				    executeRExpr +
    				   ((typeL.equals("int") && typeR.equals("int"))? "slt $6, $5, $3\n" : "sltu $6, $5, $3\n" ) +
    				   ((typeL.equals("int") && typeR.equals("int"))? "slt $7, $3, $5\n" : "sltu $7, $3, $5\n" ) +
    				   "add $3, $6, $7\n";
    		}
    		else if(op.equals("LE")) {
    			return executeLExpr +
    				   "add $5, $3, $0\n" + 
    				   executeRExpr +
    				   ((typeL.equals("int") && typeR.equals("int"))? "slt $6, $5, $3\n" : "sltu $6, $5, $3\n" ) +
    				   "add $11, $3, $0\n" +
    				   "lis $3\n" +
    				   ".word 1\n" +
    				   "beq $6, $3, 2\n" +
    				   "beq $5, $11, 1\n" +
    				   "sub $3, $3, $3\n";
    		}
    		else if(op.equals("GE")) {
    		   return executeLExpr +
    				  "add $5, $3, $0\n" +
    				  executeRExpr +
    				  ((typeL.equals("int") && typeR.equals("int"))? "slt $6, $3, $5\n" : "sltu $6, $3, $5\n" ) +
    				  "add $11, $3, $0\n" +
    				  "lis $3\n" +
    				  ".word 1\n" +
    				  "beq $6, $3, 2\n" +
    				  "beq $5, $11, 1\n" +
    				  "sub $3, $3, $3\n";
    		}
    		else {
    			return executeLExpr +
    				   "add $5, $3, $0\n" +
    				   executeRExpr +
    				   "add $11, $3, $0\n"+
    				   "lis $3\n" +
     				   ".word 1\n" +
    				   "beq $11,$5,1\n" +
     				   "sub $3, $3, $3\n";
    		}
    	}
    	else if(lhs.equals("expr")) {
    		if(t.rule.size() == 2) {
    			Tree term = t.children.get(0);
    			return codeGenerator(term);
    		}
    		else {
    			Tree expr = t.children.get(0);
				Tree term = t.children.get(2);
				String type1 = checkStatementType(expr);
				String type2 = checkStatementType(term);
				String executeExpr = codeGenerator(expr);
				String executeTerm = codeGenerator(term);
				String temp = t.rule.get(2);
    			if(temp.equals("PLUS")) {			
    				if(type1.equals("int") && type2.equals("int")) { 
    				   return executeExpr + push("$3") + 
    					      executeTerm + pop("$8") +
    					      "add $3, $8, $3\n";
    				}
    				else if(type1.equals("int*") && type2.equals("int")) {
    				   return executeExpr + push("$3") +
    						  executeTerm + "mult $3, $4\n" +
    						  "mflo $3\n" + pop("$8") + "add $3, $8, $3\n";
    				}
    				else {
    				   return executeExpr + push("$3") +
    						  executeTerm + pop("$8") +
    						  "mult $8, $4\n" + "mflo $8\n"+ 
    						  "add $3, $3, $8\n";
    				}
    			}
    			else {
    				if(type1.equals("int") && type2.equals("int")) {
    				   return executeExpr + push("$3") +
    					      executeTerm + pop("$8") +
    					      "sub $3, $8, $3\n";
    				}
    				else if(type1.equals("int*") && type2.equals("int*")) {
    				   return executeExpr + push("$3") +
    						  executeTerm + pop("$8") +
    						  "sub $3, $8, $3\n" +
    						  "div $3, $4\n" +
    						  "mflo $3\n";
    				}
    				else {
    				   return executeExpr + push("$3") +
     						  executeTerm + "mult $3, $4\n" +
    						  "mflo $3\n" + pop("$8") + "sub $3, $8, $3\n";
    				}
    			}
    		}
    	}
    	else if(lhs.equals("term")) {
    		if(t.rule.size() == 2) {
    			Tree factor = t.children.get(0);
    			return codeGenerator(factor);
    		}
    		else {
    			Tree term = t.children.get(0);
    		    Tree factor = t.children.get(2);
    		    String executeTerm = codeGenerator(term);
    		    String executeFactor = codeGenerator(factor);
    		    String temp = t.rule.get(2);
    		    if(temp.equals("STAR")) {
    		    	return executeTerm + push("$3") +
    		    		   executeFactor + pop("$8") +
    		    		   "mult $3, $8\n" +
    		    		   "mflo $3\n";
    		    }
    		    else if(temp.equals("SLASH")) {
    		    	return executeTerm + push("$3") +
     		    		   executeFactor + pop("$8") +
     		    		   "div $8, $3\n" +
     		    		   "mflo $3\n";
    		    }
    		    else {
    		    	return executeTerm + push("$3") +
      		    		   executeFactor + pop("$8") +
      		    		   "div $8, $3\n" +
      		    		   "mfhi $3\n";
    		    }
    		}
    	}
    	else if(lhs.equals("factor")) {
    		String temp = t.rule.get(1);
    		if(t.rule.size() == 2) {
    			if(temp.equals("ID")) {
    				Tree ID = t.children.get(0);
    				return codeGenerator(ID);
    			}
    			else if(temp.equals("NUM")) {
    				return "lis $3\n" +
    			           ".word" + " " + t.children.get(0).rule.get(1) + "\n";
    			}
    			else {
    				return "add $3, $0, $0\n";
    			}
    		}
    		else if(temp.equals("AMP")) {
    			String decide = t.children.get(1).rule.get(1);
    			if(decide.equals("STAR")) {
    				Tree factor = t.children.get(1).children.get(1);
    				return codeGenerator(factor);
    			}
    			else {
    			    Tree lvalue = t.children.get(1);
    			    String executeLvalue = codeGenerator(lvalue);
    			    Integer offsetOfLvalue = offsetTable.get(executeLvalue);
    			    return "lis $3\n" +
    				       ".word" + " " + offsetOfLvalue + "\n" +
    			           "add $3, $3, $29\n";	
    			}
    		}
    		else if(temp.equals("STAR")) {
    			Tree factor = t.children.get(1);
    			String executeFactor = codeGenerator(factor);
    			return executeFactor +
    				   "lw $3, 0($3)\n";
    		}
    		else if(temp.equals("LPAREN")) {
    			Tree expr = t.children.get(1);
    			return codeGenerator(expr);
    		}
    		else {
    			Tree expr = t.children.get(3);
    			String executeExpr = codeGenerator(expr);
    			return executeExpr +
    				   "add $1, $3, $0\n" +
    			       push("$31") +
    			       "jalr $21\n" +
    			       pop("$31");
    		}
    	}
    	else if(lhs.equals("ID")) {
    		String lexeme = t.rule.get(1);
    		Integer idOffset = offsetTable.get(lexeme);
    		return "lw $3,"+ idOffset + "($29)\n";
    	}
    	else {}
    	return "";
    }
   
    public void go() {
    	symbolTable = new HashMap<String,String>();
    	offsetTable = new HashMap<String,Integer>();
        Tree parseTree = readParse("S");
        genSymbols(parseTree);
        checkStatementType(parseTree);
        String mips = codeGenerator(parseTree);
        System.out.println(mips);
    }
}

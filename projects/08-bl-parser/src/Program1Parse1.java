import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Leo Zhuang, Jeng Zhuang, Michael Xu
 *
 */
public final class Program1Parse1 extends Program1 {

        /*
         * Private members
         * --------------------------------------------------------
         */

        /**
         * Parses a single BL instruction from {@code tokens} returning the
         * instruction name as the value of the function and the body of the
         * instruction in {@code body}.
         *
         * @param tokens
         *                the input tokens
         * @param body
         *                the instruction body
         * @return the instruction name
         * @replaces body
         * @updates tokens
         * @requires <pre>
         * [<"INSTRUCTION"> is a prefix of tokens]  and
         *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
         * </pre>
         * @ensures <pre>
         * if [an instruction string is a proper prefix of #tokens]  and
         *    [the beginning name of this instruction equals its ending name]  and
         *    [the name of this instruction does not equal the name of a primitive
         *     instruction in the BL language] then
         *  parseInstruction = [name of instruction at start of #tokens]  and
         *  body = [Statement corresponding to the block string that is the body of
         *          the instruction string at start of #tokens]  and
         *  #tokens = [instruction string at start of #tokens] * tokens
         * else
         *  [report an appropriate error message to the console and terminate client]
         * </pre>
         */
        private static String parseInstruction(Queue<String> tokens,
                        Statement body) {
                assert tokens != null : "Violation of: tokens is not null";
                assert body != null : "Violation of: body is not null";
                assert tokens.length() > 0
                                && tokens.front().equals("INSTRUCTION") : ""
                                                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

                // TODO - fill in body
                Reporter.assertElseFatalError(
                                tokens.dequeue().equals("INSTRUCTION"),
                                "Expected: INSTRUCTION");
                String a = tokens.dequeue();
                Reporter.assertElseFatalError(Tokenizer.isIdentifier(a),
                                "Expected identifier for instruction name, got: "
                                                + a);
                Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                                "Expected: IS");
                body.parse(tokens);
                Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                                "Expected: END");
                String b = tokens.dequeue();
                Reporter.assertElseFatalError(b.equals(a),
                                "Instruction name mismatch: " + a + " vs " + b);
                return a;
                // This line added just to make the program compilable.

        }

        /*
         * Constructors
         * -----------------------------------------------------------
         */

        /**
         * No-argument constructor.
         */
        public Program1Parse1() {
                super();
        }

        /*
         * Public methods
         * ---------------------------------------------------------
         */

        @Override
        public void parse(SimpleReader in) {
                assert in != null : "Violation of: in is not null";
                assert in.isOpen() : "Violation of: in.is_open";
                Queue<String> tokens = Tokenizer.tokens(in);
                this.parse(tokens);
        }

        @Override
        public void parse(Queue<String> tokens) {
                assert tokens != null : "Violation of: tokens is not null";
                assert tokens.length() > 0 : ""
                                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

                // TODO - fill in body
                Reporter.assertElseFatalError(
                                tokens.dequeue().equals("PROGRAM"),
                                "Expected: PROGRAM");
                String programName = tokens.dequeue();
                Reporter.assertElseFatalError(
                                Tokenizer.isIdentifier(programName),
                                "Expected valid identifier for program name, got: "
                                                + programName);
                this.setName(programName);
                Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                                "Expected: IS");
                Queue<String> definedInstructionNames = new components.queue.Queue1L<>();
                while (tokens.front().equals("INSTRUCTION")) {
                        Statement instrBody = new Statement1Parse1();
                        String instrName = parseInstruction(tokens, instrBody);
                        Reporter.assertElseFatalError(!(instrName.equals("move")
                                        || instrName.equals("turnleft")
                                        || instrName.equals("turnright")
                                        || instrName.equals("infect")
                                        || instrName.equals("skip")),
                                        "Instruction name cannot be a primitive instruction: "
                                                        + instrName);
                        boolean duplicate = false;
                        int size = definedInstructionNames.length();
                        for (int i = 0; i < size; i++) {
                                String existing = definedInstructionNames
                                                .dequeue();
                                if (existing.equals(instrName)) {
                                        duplicate = true;
                                }
                                definedInstructionNames.enqueue(existing);
                        }
                        Reporter.assertElseFatalError(!duplicate,
                                        "Duplicate instruction name: "
                                                        + instrName);
                        definedInstructionNames.enqueue(instrName);
                }
                Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                                "Expected: BEGIN");
                Statement programBody = new Statement1Parse1();
                programBody.parse(tokens);
                Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                                "Expected: END");
                String endName = tokens.dequeue();
                Reporter.assertElseFatalError(endName.equals(programName),
                                "Program name mismatch: expected " + programName
                                                + ", but got " + endName);

        }

        /*
         * Main test method
         * -------------------------------------------------------
         */

        /**
         * Main method.
         *
         * @param args
         *                the command line arguments
         */
        public static void main(String[] args) {
                SimpleReader in = new SimpleReader1L();
                SimpleWriter out = new SimpleWriter1L();
                /*
                 * Get input file name
                 */
                out.print("Enter valid BL program file name: ");
                String fileName = in.nextLine();
                /*
                 * Parse input file
                 */
                out.println("*** Parsing input file ***");
                Program p = new Program1Parse1();
                SimpleReader file = new SimpleReader1L(fileName);
                Queue<String> tokens = Tokenizer.tokens(file);
                file.close();
                p.parse(tokens);
                /*
                 * Pretty print the program
                 */
                out.println("*** Pretty print of parsed program ***");
                p.prettyPrint(out);

                in.close();
                out.close();
        }

}

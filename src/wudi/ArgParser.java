package wudi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Argument parsing module.
 */
class ArgParser {

    class ParsedArguments {
        private HashMap<String, String[]> arguments = new HashMap<>();

        ParsedArguments(HashMap<String, String[]> arguments) {
            this.arguments = arguments;
        }

        boolean hasArgument(String name) {
            return arguments.containsKey(name);
        }

        String getArgument(String name) {
            if (!arguments.containsKey(name)) {
                return null;
            }

            String[] args = arguments.get(name);

            return args[0];
        }

        String[] getArguments(String name) {
            if (!arguments.containsKey(name)) {
                return null;
            }

            return arguments.get(name);
        }
    }

    private class ArgAttributes {
        String abbreviation = null;
        boolean required = false;
        int numParams = 0;
        String description = "";

        ArgAttributes(boolean required, int numParams, String description) {
            this.required = required;
            this.numParams = numParams;
            this.description = description;
        }

        ArgAttributes(String abbreviation, boolean required, int numParams, String description) {
            this.abbreviation = abbreviation;
            this.required = required;
            this.numParams = numParams;
            this.description = description;
        }
    }

    private HashMap<String, ArgAttributes> arguments = new HashMap<>();

    void registerArgument(String name, boolean required, int numParams, String description) {
        if (arguments.containsKey(name)) {
            return;
        }

        arguments.put(name, new ArgAttributes(required, numParams, description));
    }

    void registerArgument(String name, String abbreviation, boolean required, int numParams, String description) {
        if (arguments.containsKey(name)) {
            return;
        }

        arguments.put(name, new ArgAttributes(abbreviation, required, numParams, description));
    }

    ParsedArguments parse(String[] args) {
        ArrayList<String> currentParams = new ArrayList<>();
        String currentArgumentName = null;

        HashMap<String, String[]> suppliedArguments = new HashMap<>();
        ArrayList<String> errors = new ArrayList<>();

        for (String arg : args) {
            if (arg.substring(0, 1).equalsIgnoreCase("-")) {
                if (currentArgumentName != null && suppliedArguments.containsKey(currentArgumentName)) {
                    errors.add("Duplicate argument provided: " + currentArgumentName);
                } else if (currentArgumentName != null) {
                    suppliedArguments.put(currentArgumentName, currentParams.toArray(new String[currentParams.size()]));
                }

                currentArgumentName = arg.substring(1, arg.length());
                currentParams.clear();
            } else {
                currentParams.add(arg);
            }
        }

        if (currentArgumentName != null && suppliedArguments.containsKey(currentArgumentName)) {
            errors.add("Duplicate argument provided: " + currentArgumentName);
        } else if (currentArgumentName != null) {
            suppliedArguments.put(currentArgumentName, currentParams.toArray(new String[currentParams.size()]));
        }

        boolean isValid = true;
        HashMap<String, String[]> parsedArgumentsMap = new HashMap<>();

        for (Map.Entry<String, ArgAttributes> entry : arguments.entrySet()) {
            String name = entry.getKey();
            ArgAttributes attributes = entry.getValue();
            String abbr = attributes.abbreviation;

            boolean hasParsedArgument = false;
            String[] params = {};

            if (suppliedArguments.containsKey(name)) {
                hasParsedArgument = true;
                params = suppliedArguments.get(name);
            } else if (abbr != null && suppliedArguments.containsKey(abbr)) {
                hasParsedArgument = true;
                params = suppliedArguments.get(abbr);
            }

            if (attributes.required && !hasParsedArgument) {
                errors.add("Missing argument: " + name);
                isValid = false;
            }

            if (hasParsedArgument && params.length < attributes.numParams) {
                errors.add("Incorrect number of parameters supplied to " + name + ", " + attributes.numParams + " expected.");
                isValid = false;
            } else if (hasParsedArgument) {
                parsedArgumentsMap.put(name, params);
            }
        }

        if (!isValid) {
            for (String error : errors) {
                System.out.println(error);
            }
            System.exit(1);
        }

        return new ParsedArguments(parsedArgumentsMap);
    }
}

package it.giustizia.smp.utility;

public class Message {

    public final static String SEPARATOR = ";";
    private String             arguments;
    private String             argumentSeparator;
    private String             code;
    private String             text;

    public Message() {

        super();
    }

    public Message(String text, String code, String arguments, String argumentSeparator) {

        super();
        this.text = text;
        this.code = code;
        this.arguments = arguments;
        this.argumentSeparator = argumentSeparator;
    }

    /**
     * @return the arguments
     */
    public String getArguments() {

        return arguments;
    }

    /**
     * @return the argumentSeparator
     */
    public String getArgumentSeparator() {

        return argumentSeparator;
    }

    /**
     * @return the code
     */
    public String getCode() {

        return code;
    }

    /**
     * @return the text
     */
    public String getText() {

        return text;
    }

    /**
     * @param arguments
     *            the arguments to set
     */
    public void setArguments(String arguments) {

        this.arguments = arguments;
    }

    /**
     * @param argumentSeparator
     *            the argumentSeparator to set
     */
    public void setArgumentSeparator(String argumentSeparator) {

        this.argumentSeparator = argumentSeparator;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {

        this.code = code;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {

        this.text = text;
    }
}

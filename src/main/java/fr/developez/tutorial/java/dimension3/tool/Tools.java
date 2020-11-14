package fr.developez.tutorial.java.dimension3.tool;

public class Tools
{
    /**
     * Throw {@link NullPointerException} if <b>elementValue</b> is <b><i>null</i></b>
     */
    public static void checkNotNull(String elementName, Object elementValue) throws NullPointerException
    {
        if (elementValue == null)
        {
            final StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
            throw new NullPointerException(
                    elementName + " is null : " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + " at " + stackTraceElement.getLineNumber());
        }
    }
}

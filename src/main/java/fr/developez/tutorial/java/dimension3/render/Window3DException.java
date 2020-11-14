package fr.developez.tutorial.java.dimension3.render;


import fr.developez.tutorial.java.dimension3.tool.NonNull;
import fr.developez.tutorial.java.dimension3.tool.Tools;

/**
 * Exception pouvant survenir à la création de la fenêtre 3D
 */
public class Window3DException
        extends Exception
{
    public Window3DException(@NonNull String message)
    {
        super(message);
        Tools.checkNotNull("message", message);
    }

    public Window3DException(@NonNull String message, @NonNull Throwable cause)
    {
        super(message, cause);
        Tools.checkNotNull("message", message);
        Tools.checkNotNull("cause", cause);
    }
}

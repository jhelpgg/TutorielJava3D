package fr.developez.tutorial.java.dimension3.render;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

class Window3DCloseEventManager
        implements GLFWWindowCloseCallbackI
{
    /**
     * Appeler au moment qu'une fenêtre est sur la point de se fermée
     *
     * @param window Référence sur la fenêtre entrain de se fermée
     */
    @Override
    public void invoke(long window)
    {
        this.closeWidow(window);
    }

    /**
     * Gère la fermeture d'une fenêtre
     *
     * @param window Référence sur la fenêtre a fermée
     */
    void closeWidow(long window)
    {
        // Pour le moment on autorise la fenêtre à se fermée dans tous les cas
        GLFW.glfwSetWindowShouldClose(window, true);
    }
}

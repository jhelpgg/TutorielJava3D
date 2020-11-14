package fr.developez.tutorial.java.dimension3.render;

import fr.developez.tutorial.java.dimension3.tool.NonNull;
import fr.developez.tutorial.java.dimension3.tool.ThreadOpenGL;
import fr.developez.tutorial.java.dimension3.tool.Tools;
import java.nio.IntBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Window3D
{
    public final  int                       width;
    public final  int                       height;
    public final  String                    title;
    /**
     * Reference sur la fenêtre permettant à GLFW de la géréée
     */
    private       long                      window;
    private final Window3DCloseEventManager window3DCloseEventManager = new Window3DCloseEventManager();
    private       Render3D                  render3D;

    public Window3D(int width, int height, @NonNull String title)
    {
        Tools.checkNotNull("title", title);

        // Évitons les fenêtres trop petite
        this.width  = Math.max(128, width);
        this.height = Math.max(128, height);
        this.title  = title;

        // Création et lancement du Thread OpenGL
        (new Thread(this::startOpenGLThread)).start();
    }

    @ThreadOpenGL
    private void startOpenGLThread()
    {
        // Capture les erreurs LWJGL et les affichent sur la consoles
        // Une gestion de erreurs différente est possible, mais sort du tutoriel d'introduction
        GLFWErrorCallback.createPrint(System.err)
                         .set();

        if (!GLFW.glfwInit())
        {
            // On désinscrit la gestion d'erreurs puisque nous ne pouvons pas continuer
            GLFW.glfwSetErrorCallback(null)
                .free();
            System.err.println("GLFW initialisation failed");
            return;
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        // On empêche, pour le moment, que la fenêtre s'affiche afin d'avoir le temps de la configurée
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        // La fenêtre aura un contour
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        // La fenêtre ne pourra pas être redimensionnée
        // Un redimensionnement obligerait à écouter le changement de taille et à y réagir.
        // Comme nous voulons faire simple, nous ne gérons pas ce cas tout de suite
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        // On veut que la fenêtre fasse aau plus proche possible la dimension demandée
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);

        // Create de la fenêtre
        this.window = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);

        if (this.window == MemoryUtil.NULL)
        {
            // La fenêtre n'a pas pu être réservée en mémoire, on ne peut pas continuer
            this.closeGLFW();
            System.err.println("GLFW can't create window");
            return;
        }

        // TODO : Insérer ici les listeners pour les événements souris, clavier et joystick

        // Management de la fermeture de la fenêtre pour libéré proprement la mémoire
        GLFW.glfwSetWindowCloseCallback(this.window, this.window3DCloseEventManager);

        // On réserve de la mémoire afin de capturer la taille finale de la fenêtre
        final MemoryStack memoryStack = MemoryStack.stackPush();
        // Servira pour collecter la largeur
        final IntBuffer pointerWidth = memoryStack.mallocInt(1);
        // Servira pour collecter la hauteur
        final IntBuffer pointerHeight = memoryStack.mallocInt(1);
        // Capture des dimensions
        GLFW.glfwGetWindowSize(this.window, pointerWidth, pointerHeight);

        // On récupère la configuration de l'écran afin de centrer la fenêtre
        final GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        if (videoMode != null)
        {
            // On centre la fenêtre
            GLFW.glfwSetWindowPos(
                    this.window,
                    (videoMode.width() - pointerWidth.get(0)) / 2,
                    (videoMode.height() - pointerHeight.get(0)) / 2);
        }

        // Libère la mémoire prise
        MemoryStack.stackPop();

        // Associe le context OpenGL à la fenêtre
        GLFW.glfwMakeContextCurrent(this.window);
        // Activation du mode v-sync
        GLFW.glfwSwapInterval(1);

        // Maintenant que tout est configurer la fenêtre peut se montrée
        GLFW.glfwShowWindow(this.window);

        // Cette appel est crucial pour que la management d'OpenGL avec GLFW se passe bien
        GL.createCapabilities();

        //Lancement du rendu OpenGL
        this.render3D = new Render3D(this, this.window);
        this.render3D.rendering();
    }

    public void closeWindow()
    {
        this.window3DCloseEventManager.closeWidow(this.window);
    }

    void exitWidow()
    {
        // On libère tous les listeners
        Callbacks.glfwFreeCallbacks(this.window);
        // On finalize la destruction de la fenêtre
        GLFW.glfwDestroyWindow(this.window);
        // On ferme les événements GLFW
        this.closeGLFW();
    }

    /**
     * Appeler à la sortie de la fenêtre pour nettoyer proprement les divers états
     */
    private void closeGLFW()
    {
        // Termine la gestion de la fenêtre par GLFW
        GLFW.glfwTerminate();
        // Arrête de tracker les erreurs
        GLFW.glfwSetErrorCallback(null)
            .free();
    }
}

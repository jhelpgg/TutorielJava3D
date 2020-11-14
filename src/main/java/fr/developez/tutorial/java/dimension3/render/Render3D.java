package fr.developez.tutorial.java.dimension3.render;

import fr.developez.tutorial.java.dimension3.tool.GLU;
import fr.developez.tutorial.java.dimension3.tool.NonNull;
import fr.developez.tutorial.java.dimension3.tool.ThreadOpenGL;
import fr.developez.tutorial.java.dimension3.tool.Tools;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

class Render3D
{
    private final Window3D window3D;
    private final long     window;

    Render3D(@NonNull Window3D window3D, long window)
    {
        Tools.checkNotNull("window3D", window3D);
        this.window3D = window3D;
        this.window   = window;
    }

    @ThreadOpenGL
    public void rendering()
    {
        this.initialize3D();

        // Tant que la fenêtre est présente, rafraichir la 3D
        while (!GLFW.glfwWindowShouldClose(this.window))
        {
            // Nettoyage de la fenêtre en buffer pour la prochaine frame
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Dessine la frame courante
            this.drawScene();

            // Rend la fenêtre en buffer dessinée visible à l'écran
            GLFW.glfwSwapBuffers(this.window);

            // Récupération des événements fenêtre arrivée peandant le dessin de la frame.
            // Les listeners sur les événements claviers seront appelée par cette méthode
            GLFW.glfwPollEvents();

            // TODO traité les évènements survenus
        }

        // La fenêtre de rendu se ferme ou est fermée, on quitte tout proprement
        this.window3D.exitWidow();
    }

    @ThreadOpenGL
    private void initialize3D()
    {
        final int width  = this.window3D.width;
        final int height = this.window3D.height;

        // *** Gestion de la transparence ***
        // On indique que l'on souhaite activé la tranparence
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        // Spécification de la précision de la transparence
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.01f);
        // Façon dont la transparence est calculée
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        /// *** Apparence globale ***
        // Les matériaux peuvent être colorés
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        // Pour des raisons de performances, on désactive les textures de manière globale, on les activera au cas par cas
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        // On active la possibilité de combiné des effets
        GL11.glEnable(GL11.GL_BLEND);

        // ** Réglage de la perspective  ***
        // On fixe le rectangle de la fenêtre où la 3D sera dessinée.
        // Ici on veut dessiner sur toute la fenêtre
        // Sur le "frustum", c'est la zone proche de l'utilisateur
        // S'appel aussi 'view port'
        GL11.glViewport(0, 0, width, height);
        // On veut une projection normalisée
        GL11.glEnable(GL11.GL_NORMALIZE);

        // Réglage de la projection
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        final double ratio = (double) width / (double) height;
        GLU.gluPerspective(45.0, ratio, 0.1, 5000.0);
        // Réglage du model de la vue
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // Au départ on met un écran blanc
        GL11.glClearColor(1f, 1f, 1f, 1f);
        //On active le test de profondeur
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Active les face visible que d'un coté (Pour éviter de dessiner des choses qui seront toujours cachés)
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_FRONT);

        // Éclairage de base pour un effet agréable
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, GL11.GL_TRUE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLightModeli(GL12.GL_LIGHT_MODEL_COLOR_CONTROL, GL12.GL_SEPARATE_SPECULAR_COLOR);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, 1);

        // Active l'éclairage
        //GL11.glEnable(GL11.GL_LIGHTING);
    }

    // Coordonnées de l'objet
    private float triangleX = 0f;
    private float triangleY = 0f;
    private float triangleZ = -1f;

    // Pas de translation
    private final float step = 0.01f;
    // Sens de translation
    private       int   way  = 1;

    /**
     * Dessine la scène 3D
     */
    @ThreadOpenGL
    private void drawScene()
    {
        // Efface l'écran en blanc
        GL11.glClearColor(1f, 1f, 1f, 1f);

        // Dessin du triangle

        // Utilise la couleur rouge
        GL11.glColor4f(1f, 0f, 0f, 1f);

        // Sauvegarde la matrice courante dans la pile des matrices
        GL11.glPushMatrix();
        // Change la matrice courant afin de positionner le triangle à l'écran
        GL11.glTranslatef(this.triangleX, this.triangleY, this.triangleZ);

        // Début du polygone
        GL11.glBegin(GL11.GL_POLYGON);

        // Chaque point du polygone dans le sens des aiguilles d'une montre
        // Définit en relatif à la matrice courante
        GL11.glVertex3f(0.0f, 0.5f, 0.0f);
        GL11.glVertex3f(0.5f, -0.5f, 0.0f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.0f);

        // Fin du polygone
        GL11.glEnd();
        // Restaure la matrice sauvegardée
        GL11.glPopMatrix();

        // On met à jour la position pour le prochain tour
        this.triangleX += this.step * this.way;

        if (this.triangleX >= 1.5f)
        {
            this.way = -1;
        }

        if (this.triangleX <= -1.5f)
        {
            this.way = 1;
        }
    }
}

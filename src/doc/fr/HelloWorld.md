# Création d'un "Hello world"

Ce Hello world va consisté à afficher un triangle rouge.

Nous supposonci ici que vous un [environment de travial](Introduction.md#crer-un-environnement-de-travail)

Et nous supposons également que vous aillez la [fenêtre de rendu](Introduction.md#crer-une-fentre-daffichage-pour-nos-scenes-3d) complète 

## Dessinons un triangle rouge

Nous verrons dans le chapitre suivant la notion de graphe de scène (scene graph en Anglais) et comment le mettre en place.

Pour l'instant, nous allons juste dessiner notre tringle directement dans la boucle de rafraichissement du thread OpenGL.

À chaque tour de la boucle on redessine le triangle.

Comme nous l'avons vu dans l'introduction, les couleurs sont des valeurs d'intensité de rouge, vert et bleu. Avec un notion de transparence. 
Ici on veut du rouge, donc le rouge à fond, pas de bleu, pas de vert, et une couleur opaque. 
Il existe plusieurs façons de spécifier une couleur en OpenGL. 
Ici nous prendrons la versions avec des **float**. Cette version fait varier les valeurs entre 0 et 1. 
Ou 0 absence totale, 1 intensité complète. 
Cette notation à l'avantage d'être plus souple que la version avec des entiers et ne pas prendre trop de mémoire par rapport à la version en double.

Dans la mesure du possible on utilisera des **int** ou des **float** pour des raisons de mémoire et/ou de performances.
Nous verrons plus loin comment optimiser les performances.


```java
// ...
import fr.developez.tutorial.java.dimension3.tool.ThreadOpenGL;
import org.lwjgl.opengl.GL11;
// ...

class Render3D
{
    // ...
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

        // Début du polygone
        GL11.glBegin(GL11.GL_POLYGON);
    
        // Chaque point du polygone dans le sens des aiguilles d'une montre
        GL11.glVertex3f(0.0f, 0.5f, -1.0f);
        GL11.glVertex3f(0.5f, -0.5f, -1.0f);
        GL11.glVertex3f(-0.5f, -0.5f, -1.0f);

        // Fin du polygone
        GL11.glEnd();
    }
}
```

N'oublions pas que la méthode `drawScene` est appelée en boucle pour chaque frame.
C'est pour cela que nous mettons l'écran en blanc au début afin de partir à chaque fois d'une page vierge.
Si on ne le faisait pas on cumulerait avec l'affichage précédent. Ici on ne verrait pas la différence puisque l'on déssine à chaque fois exactement la même chose.
Mais c'est rarement le cas. Donc prenons l'habitude dès maintenant en commençant par `GL11.glClearColor(1f, 1f, 1f, 1f);`

Les quatre nombres sont respectivement, la quantité de rouge, de vert et de blue. Le quatrième est la transparence.
La transparence de la couleur qui efface l'écran sera pour nous toujours à 1 (opaque). D'autre valeur de transparence sont dédiés à certains effets.


On veut colorier le triangle en rouge, du coup on change la couleur de remplissage courante par du rouge avec `GL11.glColor4f(1f, 0f, 0f, 1f);`

 Pour chaque face de la maille de notre objet, il faut dire à OpenGL quand la description de la face commence et quand celle-ci finit.
 C'est le rôle du couple `GL11.glBegin(GL11.GL_POLYGON);` , `GL11.glEnd();`
 
![Attention](../images/warning.png) Attention
> À chaque `glBegin` doit toujours correspondre un `glEnd` 
>
> Un `glEnd` doit avoir un `glBegin` précédent qui lui correspond 

Les instructions `GL11.glVertex3f` définissent les coordonées d'un sommet du polygone. 
On fait bien attention de définir dans le sens des aiguilles d'un montre. 
Car à cause de no réglages OpenGL, le définir dans l'autre sens rendrait le triangle non visible.

On remarque que le Z choisit est **-1.0f** et non pas **0.0f** 
En effet, on rappel que c'est l'observateur qui se trouve en (0, 0, 0).
Ensuite, le frustum, fait qu'il y a une distance entre l'observateur et l'écran. L'écran est sur la zone proche du frustum.

On a finalement notre "Hello world"

![Hello world](../images/HelloWorld.PNG)

## Un petit peu plus

Avant de parler du graphe de scène, nous allons animer un peu notre triangle

Afin de simplifier l'animation, nous allons non plus placer notre triangle en absolu, mais en relatif par rapport à une position qui pourra changer.

```java
// ...
import fr.developez.tutorial.java.dimension3.tool.ThreadOpenGL;
import org.lwjgl.opengl.GL11;
// ...

class Render3D
{
    // ...

    // Coordonnées de l'objet
    private float triangleX = 0f;
    private float triangleY = 0f;
    private float triangleZ = -1f;
    // ...

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
    }
}
``` 

Désormais, on utilise la pile des matrices détaillée dans l'introduction.

`GL11.glPushMatrix();` sauvegarde en haut de la pile des matrices l'état de la matrice courante

`GL11.glPopMatrix();` retire l'état de la matrice en haut de la pile et l'utilise comme état courant.

`GL11.glTranslatef` applique à la matrice courante une translation, qui va bouger toutes les coordonnées du vecteur donné.

On remarque que les Z dans les `glVertex3f` valent **0.0f** en effet maintenant on est en relatif.

Si on lance l'application, on ne constate aune différence visuelle.

Animons cela maintenant.

Pour animé notre triangle, il suffit de faire varier les coordonnées du triangle entre chaque boucle.

 ```java
 // ...
 import fr.developez.tutorial.java.dimension3.tool.ThreadOpenGL;
 import org.lwjgl.opengl.GL11;
 // ...
 
 class Render3D
 {
     // ...
 
     // Coordonnées de l'objet
     private float triangleX = 0f;
     private float triangleY = 0f;
     private float triangleZ = -1f;

    // Pas de translation
    private final float step = 0.01f;
    // Sens de translation
    private       int   way  = 1;
     // ...
 
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
 ``` 

On a désormais un triangle qui bouge à l'écran

Comme on le remarque, ça ne change absolument pas le code de l'affichage de l'objet, c'est l'avantage d'utiliser une position relative.

Autre remarque, les coordonnées auraient pu être modifié depuis un autre thread. 
Ce qui permet de laisser le développeur modifier les coordonnées sans se préoccuper du thread OpenGL, de faire un moteur d'animation, ...

Amusez-vous à faire vos propres essais. 

Pour information

` GL11.glRotatef(angle, x, y, z)` effectue une rotation de l'angle demandé (en degré) autour de l'axe dont les coordonnés sont précisés avec (x, y, z)

Pour une rotation autour de X : `GL11.glRotatef(angleX, 1f, 0f, 0f)`
Pour une rotation autour de Y : `GL11.glRotatef(angleY, 0f, 1f, 0f)`
Pour une rotation autour de Z : `GL11.glRotatef(angleZ, 0f, 0f, 1f)`

`GL11.glScalef(scaleX, scaleY, scaleZ)` change les proportions selon X, Y, Z. 
Pour une déformation homogène il suffit de mettre la même valeur pour X, Y et Z.

Rendez-vous dans [Graphe de scène](SceneGraphe.md) pour parler du graphes de scène 

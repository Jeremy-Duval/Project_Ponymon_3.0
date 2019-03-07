package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Createur de map.
 * Placer cette classe dans le package model.
 * Utiliser setObjectToAllLine ou setObject.
 * Il y a 5 lignes (si emploi de setObject), 5 colonnes, 15 tours.
 * Les numérotations vont de 0 à 5.
 * @author jeremy
 */
public class mapCreator {

    public static void main(String[] args) {
        Map tmp1;
        Map tmp2;
        Map tmp3;
        Map tmp4;
        Map tmp5;
        tmp1 = new Map();
        tmp2 = new Map();
        tmp3 = new Map();
        tmp4 = new Map();
        tmp5 = new Map();
        // On ajoute les objets à la map

        ////////////////////////////////////////////////////////////////////////


        for(int i = 0; i <11; i+=4) {
            tmp1.setObjectToAllLine(new ObjectPuddle(), i, 1);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i, 2);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i, 3);

            tmp1.setObjectToAllLine(new ObjectPuddle(), i+1, 1);
            tmp1.setObjectToAllLine(new ObjectObstacle(), i+1, 2);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i+1, 3);

            tmp1.setObjectToAllLine(new ObjectPuddle(), i+2, 0);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i+2, 2);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i+2, 4);

            tmp1.setObjectToAllLine(new ObjectPuddle(), i+3, 0);
            tmp1.setObjectToAllLine(new ObjectBooster(), i+3, 2);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i+3, 3);
            tmp1.setObjectToAllLine(new ObjectPuddle(), i+3, 4);
        }
        // On nomme la map
        tmp1.save("aquaLand");


        ////////////////////////////////////////////////////////////////////////

        for(int i = 0; i <10; i+=5) {
            tmp2.setObjectToAllLine(new ObjectObstacle(), i, 1);
            tmp2.setObjectToAllLine(new ObjectObstacle(), i, 3);

            tmp2.setObjectToAllLine(new ObjectObstacle(), i+1, 1);
            tmp2.setObjectToAllLine(new ObjectObstacle(), i+1, 3);

            tmp2.setObjectToAllLine(new ObjectObstacle(), i+2, 1);
            tmp2.setObjectToAllLine(new ObjectPuddle(), i+2, 2);
            tmp2.setObjectToAllLine(new ObjectObstacle(), i+2, 3);
            tmp2.setObjectToAllLine(new ObjectPuddle(), i+2, 4);

            tmp2.setObjectToAllLine(new ObjectObstacle(), i+3, 1);
            tmp2.setObjectToAllLine(new ObjectBooster(), i+3, 2);
            tmp2.setObjectToAllLine(new ObjectPuddle(), i+3, 3);
            tmp2.setObjectToAllLine(new ObjectObstacle(), i+3, 4);

            tmp2.setObjectToAllLine(new ObjectObstacle(), i+4, 1);
            tmp2.setObjectToAllLine(new ObjectBooster(), i+4, 2);
            tmp2.setObjectToAllLine(new ObjectObstacle(), i+4, 3);
            tmp2.setObjectToAllLine(new ObjectBooster(), i+4, 4);
        }
        // On nomme la map
        tmp2.save("pikou");

        ////////////////////////////////////////////////////////////////////////

        tmp3.save("empty");

        ////////////////////////////////////////////////////////////////////////


        for(int i = 0; i <10; i+=5) {
            tmp4.setObjectToAllLine(new ObjectBooster(), i, 1);
            tmp4.setObjectToAllLine(new ObjectObstacle(), i, 2);
            tmp4.setObjectToAllLine(new ObjectPuddle(), i, 4);

            tmp4.setObjectToAllLine(new ObjectObstacle(), i+1, 1);
            tmp4.setObjectToAllLine(new ObjectBooster(), i+1, 2);
            tmp4.setObjectToAllLine(new ObjectObstacle(), i+1, 3);

            tmp4.setObjectToAllLine(new ObjectBooster(), i+2, 1);
            tmp4.setObjectToAllLine(new ObjectPuddle(), i+2, 2);
            tmp4.setObjectToAllLine(new ObjectBooster(), i+2, 3);
            tmp4.setObjectToAllLine(new ObjectPuddle(), i+2, 4);

            tmp4.setObjectToAllLine(new ObjectBooster(), i+3, 1);
            tmp4.setObjectToAllLine(new ObjectObstacle(), i+3, 2);
            tmp4.setObjectToAllLine(new ObjectBooster(), i+3, 3);
            tmp4.setObjectToAllLine(new ObjectObstacle(), i+3, 4);

            tmp4.setObjectToAllLine(new ObjectObstacle(), i+4, 1);
            tmp4.setObjectToAllLine(new ObjectBooster(), i+4, 2);
            tmp4.setObjectToAllLine(new ObjectObstacle(), i+4, 3);
            tmp4.setObjectToAllLine(new ObjectPuddle(), i+4, 4);
        }
        // On nomme la map
        tmp4.save("boosted");

        ////////////////////////////////////////////////////////////////////////

        for(int i = 0; i <8; i+=7) {
            tmp5.setObjectToAllLine(new ObjectPuddle(), i, 1);
            tmp5.setObjectToAllLine(new ObjectBooster(), i, 2);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i, 3);
            tmp5.setObjectToAllLine(new ObjectBooster(), i, 4);


            tmp5.setObjectToAllLine(new ObjectPuddle(), i+1, 0);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+1, 1);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+1, 3);


            tmp5.setObjectToAllLine(new ObjectPuddle(), i+2, 0);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+2, 1);
            tmp5.setObjectToAllLine(new ObjectBooster(), i+2, 2);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+2, 3);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+2, 4);


            tmp5.setObjectToAllLine(new ObjectBooster(), i+3, 0);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+3, 1);
            tmp5.setObjectToAllLine(new ObjectBooster(), i+3, 2);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+3, 3);

            tmp5.setObjectToAllLine(new ObjectObstacle(), i+4, 1);
            tmp5.setObjectToAllLine(new ObjectBooster(), i+4, 2);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+4, 3);
            tmp5.setObjectToAllLine(new ObjectPuddle(), i+4, 4);

            tmp5.setObjectToAllLine(new ObjectObstacle(), i+5, 0);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+5, 2);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+5, 4);

            tmp5.setObjectToAllLine(new ObjectBooster(), i+6, 0);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+6, 1);
            tmp5.setObjectToAllLine(new ObjectObstacle(), i+6, 3);
        }
        // On nomme la map
        tmp5.save("jump");

    }
}

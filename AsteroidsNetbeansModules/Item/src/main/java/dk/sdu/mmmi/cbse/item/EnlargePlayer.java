package dk.sdu.mmmi.cbse.item;

public class EnlargePlayer extends Item implements IPowerUp {

    public EnlargePlayer() {
        assignTexture("/img/enlargeplayer.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public void playerEffect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

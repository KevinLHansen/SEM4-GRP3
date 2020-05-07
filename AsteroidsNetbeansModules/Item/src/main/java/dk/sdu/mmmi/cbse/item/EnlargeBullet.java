package dk.sdu.mmmi.cbse.item;

public class EnlargeBullet extends Item implements IPowerUp {

    public EnlargeBullet() {
        assignTexture("/img/enlargebullet.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public void playerEffect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

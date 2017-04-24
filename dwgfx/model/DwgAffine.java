package dwgfx.model;

import javax.xml.bind.annotation.*;

import javafx.scene.transform.Affine;

@XmlAccessorType(XmlAccessType.FIELD)
public class DwgAffine {
	@XmlAttribute private double mxx;
	@XmlAttribute private double mxy;
	@XmlAttribute private double tx;
	@XmlAttribute private double myx;
	@XmlAttribute private double myy;
	@XmlAttribute private double ty;
	
	public DwgAffine() {
		mxx = myy = 1.0;
		mxy = myx = tx = ty = 0.0;
	}
	
	public DwgAffine(Affine trans) {
		mxx = trans.getMxx();
		mxy = trans.getMxy();
		tx = trans.getTx();
		myx = trans.getMyx();
		myy = trans.getMyy();
		ty = trans.getTy();
	}
	
	public Affine getTransform() {
		return new Affine(mxx, mxy, tx, myx, myy, ty);
	}
}
package system.allcommonclasses.transformations;

import java.math.BigInteger;

/**
 * 
 * For when you don't really want to transform, but someone forced you to use a transformation.
 *
 */
public class NoChange extends Transformation {

	@Override
	public BigInteger transform(BigInteger template) {
		return template;
	}

}

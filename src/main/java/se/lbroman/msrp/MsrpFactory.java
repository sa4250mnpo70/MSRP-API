package se.lbroman.msrp;

/**
 * This class instanciate the implementation of the MsrpStack
 * 
 * @author Leonard Broman
 * 
 */
public class MsrpFactory {

	private static String path = "se.lbroman.msrp.impl";
	private static String implementationClass = ".MsrpStackImpl";

	/**
	 * Set the path to the class .MsrpStackImpl . The default for this
	 * implementation is se.lbroman.msrp.impl
	 * 
	 * @param path
	 *            the path to the class .MsrpStackImpl
	 */
	public static void setPath(String path) {
		MsrpFactory.path = path;
	}

	/**
	 * Instanciates a new MsrpStackImpl where the path points.
	 * 
	 * @return an MsrpStack
	 * @throws InstantiationException
	 *             if the class could not be instantiated.
	 * @throws IllegalAccessException
	 *             if you do not have the rights to perform this.
	 * @throws ClassNotFoundException
	 *             if the class could not be found by the classloader.
	 */
	public static MsrpStack createStack() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		return (MsrpStack) Class.forName(path + implementationClass).newInstance();
	}

}

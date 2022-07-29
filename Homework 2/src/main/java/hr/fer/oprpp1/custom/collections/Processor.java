package hr.fer.oprpp1.custom.collections;
/**
 *This interface is used as a processor. It has only one method witch is 
 *used to some useful work on given object.
 * 
 *
 */
public interface Processor {
	
	/**
     *This method is used to do some useful work on given object.
     *
     *@param value Object where work will be done.
     */
	void process(Object value);
}


package cars;

import javax.swing.JFrame;
/**
 *
 * @author DIMAS
 */
public class Cars extends JFrame{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
        JFrame app=new JFrame();
        work w=new work();
        app.add(w);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(500,720);
        app.setVisible(true);
    }
    
}

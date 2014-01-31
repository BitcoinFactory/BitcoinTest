package Bitcoin;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class GetAction extends AbstractAction {
	
	private static final long serialVersionUID = 1;
	private ClientWindow cw; 
 
	public GetAction(ClientWindow cw, String texte){
		super(texte);
 
		this.cw = cw;
	}
 
	public void actionPerformed(ActionEvent e) { 
		String login = cw.getLogin().getText();
		String password = cw.getPassword().getText();
		
		System.out.println(login+" "+password);
		
		new ClientSQL(login, password);
	}

}
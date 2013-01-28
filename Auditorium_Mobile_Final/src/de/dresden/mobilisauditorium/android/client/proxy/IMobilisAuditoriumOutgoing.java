package de.dresden.mobilisauditorium.android.client.proxy;

import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

public interface IMobilisAuditoriumOutgoing {

//	void sendXMPPBean( XMPPBean out, IXMPPCallback< ? extends XMPPBean > callback );

	void sendXMPPBean( XMPPBean out );

}
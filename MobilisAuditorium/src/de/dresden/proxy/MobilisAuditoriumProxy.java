package de.dresden.proxy;

import java.util.List;import java.util.ArrayList;public class MobilisAuditoriumProxy {

	private IMobilisAuditoriumOutgoing _bindingStub;


	public MobilisAuditoriumProxy( IMobilisAuditoriumOutgoing bindingStub) {
		_bindingStub = bindingStub;
	}


	public IMobilisAuditoriumOutgoing getBindingStub(){
		return _bindingStub;
	}


}
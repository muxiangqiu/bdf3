package com.bstek.bdf3.sample;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.listener.GenericObjectListener;
import com.bstek.dorado.view.widget.action.UpdateAction;
import com.bstek.dorado.view.widget.action.UpdateItem;

@Component
public class GenericUpdateActionListener extends GenericObjectListener<UpdateAction> {


	@Override
	public boolean beforeInit(UpdateAction updateAction) throws Exception {
		return true;
	}

	@Override
	public void onInit(UpdateAction updateAction) throws Exception {
		List<UpdateItem> items = updateAction.getUpdateItems();
		if (items != null) {
			for (UpdateItem item : items) {
				item.setSubmitOldData(true);
			}
		}
	}

}

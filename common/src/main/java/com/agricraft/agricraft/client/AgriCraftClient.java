package com.agricraft.agricraft.client;

import com.agricraft.agricraft.api.AgriClientApi;
import com.agricraft.agricraft.client.tools.journal.drawers.FrontPageDrawer;
import com.agricraft.agricraft.client.tools.journal.drawers.GeneticsPageDrawer;
import com.agricraft.agricraft.client.tools.journal.drawers.GrowthReqsPageDrawer;
import com.agricraft.agricraft.client.tools.journal.drawers.IntroductionPageDrawer;
import com.agricraft.agricraft.client.tools.journal.drawers.MutationPageDrawer;
import com.agricraft.agricraft.client.tools.journal.drawers.PlantPageDrawer;
import com.agricraft.agricraft.common.item.journal.FrontPage;
import com.agricraft.agricraft.common.item.journal.GeneticsPage;
import com.agricraft.agricraft.common.item.journal.GrowthReqsPage;
import com.agricraft.agricraft.common.item.journal.IntroductionPage;
import com.agricraft.agricraft.common.item.journal.MutationsPage;
import com.agricraft.agricraft.common.item.journal.PlantPage;

public class AgriCraftClient {

	public static void init() {
		AgriClientApi.registerPageDrawer(FrontPage.ID, new FrontPageDrawer());
		AgriClientApi.registerPageDrawer(IntroductionPage.ID, new IntroductionPageDrawer());
		AgriClientApi.registerPageDrawer(GrowthReqsPage.ID, new GrowthReqsPageDrawer());
		AgriClientApi.registerPageDrawer(GeneticsPage.ID, new GeneticsPageDrawer());
		AgriClientApi.registerPageDrawer(PlantPage.ID, new PlantPageDrawer());
		AgriClientApi.registerPageDrawer(MutationsPage.ID, new MutationPageDrawer());
	}

}

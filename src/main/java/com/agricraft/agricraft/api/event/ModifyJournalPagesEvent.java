package com.agricraft.agricraft.api.event;

import com.agricraft.agricraft.api.tools.journal.JournalPage;
import net.neoforged.bus.api.Event;

import java.util.List;

/**
 * The event is fired whenever the journal's content are created/updated, for instance when a new plant is discovered.
 * It can be used to modify the contents of the journal by adding, removing, and modifying pages.
 * At the end of the event, the pages which are in the list will be the ones that appear in the journal.
 * Remark that this event is also fired when journal data is read from data components.
 *
 * This event is not cancellable.
 */
public class ModifyJournalPagesEvent extends Event {

	private final List<JournalPage> pages;

	public ModifyJournalPagesEvent(List<JournalPage> pages) {
		this.pages = pages;
	}

	public List<JournalPage> pages() {
		return pages;
	}

}

package com.leute.rank_system.bot.discord.command.container;

/**
 * An object that can be flipped through like a book.
 */
public interface PageNavigable {
    /**
     * Next page.
     * <p>
     * Can be invoked an infinite number of times.
     */
    void next();

    /**
     * Previous page.
     * <p>
     * Can be invoked an infinite number of times.
     */
    void previous();

    /**
     * @return true if there is a next page
     */
    boolean hasNext();

    /**
     * @return true if there is a previous page
     */
    boolean hasPrevious();
}

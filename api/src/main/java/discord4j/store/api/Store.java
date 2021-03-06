/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J. If not, see <http://www.gnu.org/licenses/>.
 */
package discord4j.store.api;

import discord4j.store.api.primitive.LongObjStore;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * This provides an active data connection to a store's data source, supporting read and write operations.
 *
 * @param <K> The key type which provides a 1:1 mapping to the value type. This type is also expected to be
 * {@link Comparable} in order to allow for range operations.
 * @param <V> The value type, these follow
 * <a href="https://en.wikipedia.org/wiki/JavaBeans#JavaBean_conventions">JavaBean</a> conventions.
 * @see LongObjStore
 */
public interface Store<K extends Comparable<K>, V> extends ReadOnlyStore<K, V> {

    /**
     * Stores a key value pair.
     *
     * @param key The key representing the value.
     * @param value The value.
     * @return A mono which signals the completion of the storage of the pair.
     */
    Mono<Void> save(K key, V value);

    /**
     * Stores key value pairs.
     *
     * @param entryStream A flux providing the key value pairs.
     * @return A mono which signals the completion of the storage of the pairs.
     */
    Mono<Void> save(Publisher<Tuple2<K, V>> entryStream);

    /**
     * Deletes a value associated with the provided id.
     *
     * @param id The id of the value to delete.
     * @return A mono which signals the completion of the deletion of the value.
     */
    Mono<Void> delete(K id);

    /**
     * Deletes the values associated with the provided ids.
     *
     * @param ids A stream of ids to delete values for.
     * @return A mono which signals the completion of the deletion of the values.
     */
    Mono<Void> delete(Publisher<K> ids);

    /**
     * Deletes values within a range of ids.
     *
     * @param start The starting key (inclusive).
     * @param end The ending key (exclusive).
     * @return A mono which signals the completion of the deletion of values.
     */
    Mono<Void> deleteInRange(K start, K end);

    /**
     * Deletes all entries in the data source.
     *
     * @return A mono which signals the completion of the deletion of all values.
     */
    Mono<Void> deleteAll();

    /**
     * Invalidates the contents of the store. Once this is invoked, there is no longer a guarantee that the
     * data in the store is reliable.
     *
     * @return A mono which signals the completion of the invalidation of all values.
     */
    Mono<Void> invalidate();
}

package com.nttdata.tim.rocksdb.cli.repository;

import java.util.Optional;

public interface KVReadWriteRepository<K, V> {

	boolean put ( K key, V value );

	Optional<V> get ( K key );

	boolean delete ( K key );
}
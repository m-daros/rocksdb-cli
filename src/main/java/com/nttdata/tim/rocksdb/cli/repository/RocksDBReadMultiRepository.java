package com.nttdata.tim.rocksdb.cli.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ConditionalOnProperty ( value="db.multi.folder.read", havingValue = "true", matchIfMissing = false )
@Repository
public class RocksDBReadMultiRepository implements KVRepository<String, Object> {

	@Value ( "#{'${rocksdb.folder.paths}'.split(',')}" )
	private List<String> dbFolderPaths;

	List<RocksDB> dbs;


	@PostConstruct
	void init () {

		RocksDB.loadLibrary ();
		final Options options = new Options ();
		options.setCreateIfMissing ( true );

		this.dbs = dbFolderPaths.stream ()
			.map ( dbFolderPath -> {

				try {

					log.info ( "Opening RocksDB instance using folder: {}", dbFolderPath );

					return RocksDB.open ( options, dbFolderPath );
				}
				catch ( Exception e ) {

					log.error ( "Unable to open RocksDB instance using folder: {}", dbFolderPath );

					return null;
				}
			} )
			.filter ( Objects::nonNull )
			.collect ( Collectors.toList () );

		log.info ( "RocksDB RocksDBReadMultiRepository initialized" );
	}

	@Override
	public boolean put ( String key, Object value ) {

		throw new UnsupportedOperationException ( "Operation put not supported" );
	}

	@Override
	public Optional<Object> get ( String key ) {

		List<Object> results =  dbs.stream ()
			.map ( db -> {
				try {

					return db.get ( key.getBytes () );
				}
				catch ( RocksDBException e ) {

					log.error ( "Error retrieving the entry with key: {}, cause: {}, message: {}", key, e.getCause (), e.getMessage () );

					return null;
				}
			} )
			.filter ( Objects::nonNull )
			.map ( SerializationUtils::deserialize )
			.toList ();

		if ( ! results.isEmpty () ) {

			log.info ( "finding key '{}' returns '{}'", key, results );

			return Optional.of ( results );
		}
		else {

			return Optional.empty ();
		}
	}

	@Override
	public boolean delete ( String key ) {

		throw new UnsupportedOperationException ( "Operation delete not supported" );
	}
}
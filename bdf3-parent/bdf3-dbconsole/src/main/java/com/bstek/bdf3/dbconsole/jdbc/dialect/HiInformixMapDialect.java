package com.bstek.bdf3.dbconsole.jdbc.dialect;
import java.sql.Types;

import org.hibernate.dialect.InformixDialect;
import org.springframework.stereotype.Component;

@Component
public class HiInformixMapDialect extends InformixDialect {
    public HiInformixMapDialect() {
        super();
        registerColumnType(Types.BLOB, "BYTE" );
        registerColumnType(Types.CLOB, "TEXT");
  }
}

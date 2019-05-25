package net.yitun.basic.mybatis.support;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>枚举字典转换器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月7日 下午3:23:36 LH
 *         new file.
 * </pre>
 */
public class EnumAutoTypeHandler<E extends Enum<E> & Dict> extends BaseTypeHandler<E> {

    private Class<E> type;
    private final Map<Integer, E> dicts = new HashMap<>();

    public EnumAutoTypeHandler(Class<E> type) {
        if (null == type)
            throw new IllegalArgumentException("Type argument cannot be null");

        this.type = type;
        for (E dict : type.getEnumConstants())
            dicts.put(dict.val(), dict);
    }

    private E valueOf(int val) {
        E dict = null;
        if (null != (dict = dicts.get(val)))
            return dict;

        throw new IllegalArgumentException("Cannot convert " + val + " to " + type.getSimpleName() + " by value.");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int val, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(val, parameter.val());
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int val = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(val);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int val = rs.getInt(columnName);
        return rs.wasNull() ? null : valueOf(val);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int val = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(val);
    }

}

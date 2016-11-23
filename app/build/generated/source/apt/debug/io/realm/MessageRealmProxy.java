package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageRealmProxy extends hu.szoftarch.hf.gpschat.model.Message
    implements RealmObjectProxy, MessageRealmProxyInterface {

    static final class MessageColumnInfo extends ColumnInfo
        implements Cloneable {

        public long idIndex;
        public long senderIdIndex;
        public long senderUserNameIndex;
        public long textIndex;
        public long dateTimeIndex;
        public long serverIdIndex;

        MessageColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(6);
            this.idIndex = getValidColumnIndex(path, table, "Message", "id");
            indicesMap.put("id", this.idIndex);
            this.senderIdIndex = getValidColumnIndex(path, table, "Message", "senderId");
            indicesMap.put("senderId", this.senderIdIndex);
            this.senderUserNameIndex = getValidColumnIndex(path, table, "Message", "senderUserName");
            indicesMap.put("senderUserName", this.senderUserNameIndex);
            this.textIndex = getValidColumnIndex(path, table, "Message", "text");
            indicesMap.put("text", this.textIndex);
            this.dateTimeIndex = getValidColumnIndex(path, table, "Message", "dateTime");
            indicesMap.put("dateTime", this.dateTimeIndex);
            this.serverIdIndex = getValidColumnIndex(path, table, "Message", "serverId");
            indicesMap.put("serverId", this.serverIdIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final MessageColumnInfo otherInfo = (MessageColumnInfo) other;
            this.idIndex = otherInfo.idIndex;
            this.senderIdIndex = otherInfo.senderIdIndex;
            this.senderUserNameIndex = otherInfo.senderUserNameIndex;
            this.textIndex = otherInfo.textIndex;
            this.dateTimeIndex = otherInfo.dateTimeIndex;
            this.serverIdIndex = otherInfo.serverIdIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final MessageColumnInfo clone() {
            return (MessageColumnInfo) super.clone();
        }

    }
    private MessageColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("senderId");
        fieldNames.add("senderUserName");
        fieldNames.add("text");
        fieldNames.add("dateTime");
        fieldNames.add("serverId");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    MessageRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MessageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(hu.szoftarch.hf.gpschat.model.Message.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public long realmGet$id() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    public void realmSet$id(long value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @SuppressWarnings("cast")
    public String realmGet$senderId() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.senderIdIndex);
    }

    public void realmSet$senderId(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.senderIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.senderIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.senderIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.senderIdIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$senderUserName() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.senderUserNameIndex);
    }

    public void realmSet$senderUserName(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.senderUserNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.senderUserNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.senderUserNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.senderUserNameIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$text() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.textIndex);
    }

    public void realmSet$text(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.textIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.textIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.textIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.textIndex, value);
    }

    @SuppressWarnings("cast")
    public Date realmGet$dateTime() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.dateTimeIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.dateTimeIndex);
    }

    public void realmSet$dateTime(Date value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateTimeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.dateTimeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateTimeIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.dateTimeIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$serverId() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.serverIdIndex);
    }

    public void realmSet$serverId(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.serverIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.serverIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.serverIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.serverIdIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Message")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Message");
            realmObjectSchema.add(new Property("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("senderId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("senderUserName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("text", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("dateTime", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("serverId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("Message");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_Message")) {
            Table table = sharedRealm.getTable("class_Message");
            table.addColumn(RealmFieldType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "senderId", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "senderUserName", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "text", Table.NULLABLE);
            table.addColumn(RealmFieldType.DATE, "dateTime", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "serverId", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return sharedRealm.getTable("class_Message");
    }

    public static MessageColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_Message")) {
            Table table = sharedRealm.getTable("class_Message");
            final long columnCount = table.getColumnCount();
            if (columnCount != 6) {
                if (columnCount < 6) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 6 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 6 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 6 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final MessageColumnInfo columnInfo = new MessageColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'id' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.idIndex) && table.findFirstNull(columnInfo.idIndex) != TableOrView.NO_MATCH) {
                throw new IllegalStateException("Cannot migrate an object with null value in field 'id'. Either maintain the same type for primary key field 'id', or remove the object with null value before migration.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'id' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("senderId")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'senderId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("senderId") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'senderId' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.senderIdIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'senderId' is required. Either set @Required to field 'senderId' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("senderUserName")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'senderUserName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("senderUserName") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'senderUserName' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.senderUserNameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'senderUserName' is required. Either set @Required to field 'senderUserName' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("text")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'text' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("text") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'text' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.textIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'text' is required. Either set @Required to field 'text' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("dateTime")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'dateTime' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("dateTime") != RealmFieldType.DATE) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Date' for field 'dateTime' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.dateTimeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'dateTime' is required. Either set @Required to field 'dateTime' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("serverId")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'serverId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("serverId") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'serverId' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.serverIdIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'serverId' is required. Either set @Required to field 'serverId' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Message' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Message";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static hu.szoftarch.hf.gpschat.model.Message createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        hu.szoftarch.hf.gpschat.model.Message obj = null;
        if (update) {
            Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class), false, Collections.<String> emptyList());
                    obj = new io.realm.MessageRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.MessageRealmProxy) realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Message.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.MessageRealmProxy) realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Message.class, json.getLong("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }
        if (json.has("senderId")) {
            if (json.isNull("senderId")) {
                ((MessageRealmProxyInterface) obj).realmSet$senderId(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$senderId((String) json.getString("senderId"));
            }
        }
        if (json.has("senderUserName")) {
            if (json.isNull("senderUserName")) {
                ((MessageRealmProxyInterface) obj).realmSet$senderUserName(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$senderUserName((String) json.getString("senderUserName"));
            }
        }
        if (json.has("text")) {
            if (json.isNull("text")) {
                ((MessageRealmProxyInterface) obj).realmSet$text(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$text((String) json.getString("text"));
            }
        }
        if (json.has("dateTime")) {
            if (json.isNull("dateTime")) {
                ((MessageRealmProxyInterface) obj).realmSet$dateTime(null);
            } else {
                Object timestamp = json.get("dateTime");
                if (timestamp instanceof String) {
                    ((MessageRealmProxyInterface) obj).realmSet$dateTime(JsonUtils.stringToDate((String) timestamp));
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$dateTime(new Date(json.getLong("dateTime")));
                }
            }
        }
        if (json.has("serverId")) {
            if (json.isNull("serverId")) {
                ((MessageRealmProxyInterface) obj).realmSet$serverId(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$serverId((String) json.getString("serverId"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static hu.szoftarch.hf.gpschat.model.Message createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        hu.szoftarch.hf.gpschat.model.Message obj = new hu.szoftarch.hf.gpschat.model.Message();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$id((long) reader.nextLong());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("senderId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$senderId(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$senderId((String) reader.nextString());
                }
            } else if (name.equals("senderUserName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$senderUserName(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$senderUserName((String) reader.nextString());
                }
            } else if (name.equals("text")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$text(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$text((String) reader.nextString());
                }
            } else if (name.equals("dateTime")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$dateTime(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        ((MessageRealmProxyInterface) obj).realmSet$dateTime(new Date(timestamp));
                    }
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$dateTime(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("serverId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$serverId(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$serverId((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static hu.szoftarch.hf.gpschat.model.Message copyOrUpdate(Realm realm, hu.szoftarch.hf.gpschat.model.Message object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (hu.szoftarch.hf.gpschat.model.Message) cachedRealmObject;
        } else {
            hu.szoftarch.hf.gpschat.model.Message realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
                long pkColumnIndex = table.getPrimaryKey();
                long rowIndex = table.findFirstLong(pkColumnIndex, ((MessageRealmProxyInterface) object).realmGet$id());
                if (rowIndex != TableOrView.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.MessageRealmProxy();
                        cache.put(object, (RealmObjectProxy) realmObject);
                    } finally {
                        objectContext.clear();
                    }
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static hu.szoftarch.hf.gpschat.model.Message copy(Realm realm, hu.szoftarch.hf.gpschat.model.Message newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (hu.szoftarch.hf.gpschat.model.Message) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            hu.szoftarch.hf.gpschat.model.Message realmObject = realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Message.class, ((MessageRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((MessageRealmProxyInterface) realmObject).realmSet$senderId(((MessageRealmProxyInterface) newObject).realmGet$senderId());
            ((MessageRealmProxyInterface) realmObject).realmSet$senderUserName(((MessageRealmProxyInterface) newObject).realmGet$senderUserName());
            ((MessageRealmProxyInterface) realmObject).realmSet$text(((MessageRealmProxyInterface) newObject).realmGet$text());
            ((MessageRealmProxyInterface) realmObject).realmSet$dateTime(((MessageRealmProxyInterface) newObject).realmGet$dateTime());
            ((MessageRealmProxyInterface) realmObject).realmSet$serverId(((MessageRealmProxyInterface) newObject).realmGet$serverId());
            return realmObject;
        }
    }

    public static long insert(Realm realm, hu.szoftarch.hf.gpschat.model.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = TableOrView.NO_MATCH;
        Object primaryKeyValue = ((MessageRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((MessageRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(((MessageRealmProxyInterface) object).realmGet$id(), false);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        }
        String realmGet$senderUserName = ((MessageRealmProxyInterface)object).realmGet$senderUserName();
        if (realmGet$senderUserName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, realmGet$senderUserName, false);
        }
        String realmGet$text = ((MessageRealmProxyInterface)object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
        }
        java.util.Date realmGet$dateTime = ((MessageRealmProxyInterface)object).realmGet$dateTime();
        if (realmGet$dateTime != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, realmGet$dateTime.getTime(), false);
        }
        String realmGet$serverId = ((MessageRealmProxyInterface)object).realmGet$serverId();
        if (realmGet$serverId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.szoftarch.hf.gpschat.model.Message object = null;
        while (objects.hasNext()) {
            object = (hu.szoftarch.hf.gpschat.model.Message) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = TableOrView.NO_MATCH;
                Object primaryKeyValue = ((MessageRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((MessageRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(((MessageRealmProxyInterface) object).realmGet$id(), false);
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                }
                String realmGet$senderUserName = ((MessageRealmProxyInterface)object).realmGet$senderUserName();
                if (realmGet$senderUserName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, realmGet$senderUserName, false);
                }
                String realmGet$text = ((MessageRealmProxyInterface)object).realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
                }
                java.util.Date realmGet$dateTime = ((MessageRealmProxyInterface)object).realmGet$dateTime();
                if (realmGet$dateTime != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, realmGet$dateTime.getTime(), false);
                }
                String realmGet$serverId = ((MessageRealmProxyInterface)object).realmGet$serverId();
                if (realmGet$serverId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, hu.szoftarch.hf.gpschat.model.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = TableOrView.NO_MATCH;
        Object primaryKeyValue = ((MessageRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((MessageRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(((MessageRealmProxyInterface) object).realmGet$id(), false);
        }
        cache.put(object, rowIndex);
        String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
        }
        String realmGet$senderUserName = ((MessageRealmProxyInterface)object).realmGet$senderUserName();
        if (realmGet$senderUserName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, realmGet$senderUserName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, false);
        }
        String realmGet$text = ((MessageRealmProxyInterface)object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.textIndex, rowIndex, false);
        }
        java.util.Date realmGet$dateTime = ((MessageRealmProxyInterface)object).realmGet$dateTime();
        if (realmGet$dateTime != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, realmGet$dateTime.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, false);
        }
        String realmGet$serverId = ((MessageRealmProxyInterface)object).realmGet$serverId();
        if (realmGet$serverId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.serverIdIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Message.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.szoftarch.hf.gpschat.model.Message object = null;
        while (objects.hasNext()) {
            object = (hu.szoftarch.hf.gpschat.model.Message) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = TableOrView.NO_MATCH;
                Object primaryKeyValue = ((MessageRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((MessageRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(((MessageRealmProxyInterface) object).realmGet$id(), false);
                }
                cache.put(object, rowIndex);
                String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
                }
                String realmGet$senderUserName = ((MessageRealmProxyInterface)object).realmGet$senderUserName();
                if (realmGet$senderUserName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, realmGet$senderUserName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderUserNameIndex, rowIndex, false);
                }
                String realmGet$text = ((MessageRealmProxyInterface)object).realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.textIndex, rowIndex, realmGet$text, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.textIndex, rowIndex, false);
                }
                java.util.Date realmGet$dateTime = ((MessageRealmProxyInterface)object).realmGet$dateTime();
                if (realmGet$dateTime != null) {
                    Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, realmGet$dateTime.getTime(), false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.dateTimeIndex, rowIndex, false);
                }
                String realmGet$serverId = ((MessageRealmProxyInterface)object).realmGet$serverId();
                if (realmGet$serverId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.serverIdIndex, rowIndex, false);
                }
            }
        }
    }

    public static hu.szoftarch.hf.gpschat.model.Message createDetachedCopy(hu.szoftarch.hf.gpschat.model.Message realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        hu.szoftarch.hf.gpschat.model.Message unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (hu.szoftarch.hf.gpschat.model.Message)cachedObject.object;
            } else {
                unmanagedObject = (hu.szoftarch.hf.gpschat.model.Message)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new hu.szoftarch.hf.gpschat.model.Message();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$id(((MessageRealmProxyInterface) realmObject).realmGet$id());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$senderId(((MessageRealmProxyInterface) realmObject).realmGet$senderId());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$senderUserName(((MessageRealmProxyInterface) realmObject).realmGet$senderUserName());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$text(((MessageRealmProxyInterface) realmObject).realmGet$text());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$dateTime(((MessageRealmProxyInterface) realmObject).realmGet$dateTime());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$serverId(((MessageRealmProxyInterface) realmObject).realmGet$serverId());
        return unmanagedObject;
    }

    static hu.szoftarch.hf.gpschat.model.Message update(Realm realm, hu.szoftarch.hf.gpschat.model.Message realmObject, hu.szoftarch.hf.gpschat.model.Message newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((MessageRealmProxyInterface) realmObject).realmSet$senderId(((MessageRealmProxyInterface) newObject).realmGet$senderId());
        ((MessageRealmProxyInterface) realmObject).realmSet$senderUserName(((MessageRealmProxyInterface) newObject).realmGet$senderUserName());
        ((MessageRealmProxyInterface) realmObject).realmSet$text(((MessageRealmProxyInterface) newObject).realmGet$text());
        ((MessageRealmProxyInterface) realmObject).realmSet$dateTime(((MessageRealmProxyInterface) newObject).realmGet$dateTime());
        ((MessageRealmProxyInterface) realmObject).realmSet$serverId(((MessageRealmProxyInterface) newObject).realmGet$serverId());
        return realmObject;
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

}

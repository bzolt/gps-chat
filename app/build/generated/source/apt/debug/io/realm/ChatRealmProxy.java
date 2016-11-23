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

public class ChatRealmProxy extends hu.szoftarch.hf.gpschat.model.Chat
    implements RealmObjectProxy, ChatRealmProxyInterface {

    static final class ChatColumnInfo extends ColumnInfo
        implements Cloneable {

        public long idIndex;
        public long nameIndex;
        public long messageListIndex;
        public long serverIdIndex;
        public long typeIndex;

        ChatColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(5);
            this.idIndex = getValidColumnIndex(path, table, "Chat", "id");
            indicesMap.put("id", this.idIndex);
            this.nameIndex = getValidColumnIndex(path, table, "Chat", "name");
            indicesMap.put("name", this.nameIndex);
            this.messageListIndex = getValidColumnIndex(path, table, "Chat", "messageList");
            indicesMap.put("messageList", this.messageListIndex);
            this.serverIdIndex = getValidColumnIndex(path, table, "Chat", "serverId");
            indicesMap.put("serverId", this.serverIdIndex);
            this.typeIndex = getValidColumnIndex(path, table, "Chat", "type");
            indicesMap.put("type", this.typeIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final ChatColumnInfo otherInfo = (ChatColumnInfo) other;
            this.idIndex = otherInfo.idIndex;
            this.nameIndex = otherInfo.nameIndex;
            this.messageListIndex = otherInfo.messageListIndex;
            this.serverIdIndex = otherInfo.serverIdIndex;
            this.typeIndex = otherInfo.typeIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final ChatColumnInfo clone() {
            return (ChatColumnInfo) super.clone();
        }

    }
    private ChatColumnInfo columnInfo;
    private ProxyState proxyState;
    private RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("messageList");
        fieldNames.add("serverId");
        fieldNames.add("type");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    ChatRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ChatColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(hu.szoftarch.hf.gpschat.model.Chat.class, this);
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
    public String realmGet$name() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    public void realmSet$name(String value) {
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
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    public RealmList<hu.szoftarch.hf.gpschat.model.Message> realmGet$messageList() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (messageListRealmList != null) {
            return messageListRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.messageListIndex);
            messageListRealmList = new RealmList<hu.szoftarch.hf.gpschat.model.Message>(hu.szoftarch.hf.gpschat.model.Message.class, linkView, proxyState.getRealm$realm());
            return messageListRealmList;
        }
    }

    public void realmSet$messageList(RealmList<hu.szoftarch.hf.gpschat.model.Message> value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("messageList")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<hu.szoftarch.hf.gpschat.model.Message> original = value;
                value = new RealmList<hu.szoftarch.hf.gpschat.model.Message>();
                for (hu.szoftarch.hf.gpschat.model.Message item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.messageListIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
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

    @SuppressWarnings("cast")
    public String realmGet$type() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeIndex);
    }

    public void realmSet$type(String value) {
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
                row.getTable().setNull(columnInfo.typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Chat")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Chat");
            realmObjectSchema.add(new Property("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            if (!realmSchema.contains("Message")) {
                MessageRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add(new Property("messageList", RealmFieldType.LIST, realmSchema.get("Message")));
            realmObjectSchema.add(new Property("serverId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("Chat");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_Chat")) {
            Table table = sharedRealm.getTable("class_Chat");
            table.addColumn(RealmFieldType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "name", Table.NULLABLE);
            if (!sharedRealm.hasTable("class_Message")) {
                MessageRealmProxy.initTable(sharedRealm);
            }
            table.addColumnLink(RealmFieldType.LIST, "messageList", sharedRealm.getTable("class_Message"));
            table.addColumn(RealmFieldType.STRING, "serverId", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "type", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return sharedRealm.getTable("class_Chat");
    }

    public static ChatColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_Chat")) {
            Table table = sharedRealm.getTable("class_Chat");
            final long columnCount = table.getColumnCount();
            if (columnCount != 5) {
                if (columnCount < 5) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 5 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 5 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 5 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final ChatColumnInfo columnInfo = new ChatColumnInfo(sharedRealm.getPath(), table);

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
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("messageList")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'messageList'");
            }
            if (columnTypes.get("messageList") != RealmFieldType.LIST) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Message' for field 'messageList'");
            }
            if (!sharedRealm.hasTable("class_Message")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_Message' for field 'messageList'");
            }
            Table table_2 = sharedRealm.getTable("class_Message");
            if (!table.getLinkTarget(columnInfo.messageListIndex).hasSameSchema(table_2)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'messageList': '" + table.getLinkTarget(columnInfo.messageListIndex).getName() + "' expected - was '" + table_2.getName() + "'");
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
            if (!columnTypes.containsKey("type")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'type' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("type") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'type' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.typeIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'type' is required. Either set @Required to field 'type' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Chat' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Chat";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static hu.szoftarch.hf.gpschat.model.Chat createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        hu.szoftarch.hf.gpschat.model.Chat obj = null;
        if (update) {
            Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ChatRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("messageList")) {
                excludeFields.add("messageList");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.ChatRealmProxy) realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Chat.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ChatRealmProxy) realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Chat.class, json.getLong("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((ChatRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((ChatRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("messageList")) {
            if (json.isNull("messageList")) {
                ((ChatRealmProxyInterface) obj).realmSet$messageList(null);
            } else {
                ((ChatRealmProxyInterface) obj).realmGet$messageList().clear();
                JSONArray array = json.getJSONArray("messageList");
                for (int i = 0; i < array.length(); i++) {
                    hu.szoftarch.hf.gpschat.model.Message item = MessageRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((ChatRealmProxyInterface) obj).realmGet$messageList().add(item);
                }
            }
        }
        if (json.has("serverId")) {
            if (json.isNull("serverId")) {
                ((ChatRealmProxyInterface) obj).realmSet$serverId(null);
            } else {
                ((ChatRealmProxyInterface) obj).realmSet$serverId((String) json.getString("serverId"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                ((ChatRealmProxyInterface) obj).realmSet$type(null);
            } else {
                ((ChatRealmProxyInterface) obj).realmSet$type((String) json.getString("type"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static hu.szoftarch.hf.gpschat.model.Chat createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        hu.szoftarch.hf.gpschat.model.Chat obj = new hu.szoftarch.hf.gpschat.model.Chat();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                } else {
                    ((ChatRealmProxyInterface) obj).realmSet$id((long) reader.nextLong());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ChatRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((ChatRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("messageList")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ChatRealmProxyInterface) obj).realmSet$messageList(null);
                } else {
                    ((ChatRealmProxyInterface) obj).realmSet$messageList(new RealmList<hu.szoftarch.hf.gpschat.model.Message>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        hu.szoftarch.hf.gpschat.model.Message item = MessageRealmProxy.createUsingJsonStream(realm, reader);
                        ((ChatRealmProxyInterface) obj).realmGet$messageList().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("serverId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ChatRealmProxyInterface) obj).realmSet$serverId(null);
                } else {
                    ((ChatRealmProxyInterface) obj).realmSet$serverId((String) reader.nextString());
                }
            } else if (name.equals("type")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((ChatRealmProxyInterface) obj).realmSet$type(null);
                } else {
                    ((ChatRealmProxyInterface) obj).realmSet$type((String) reader.nextString());
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

    public static hu.szoftarch.hf.gpschat.model.Chat copyOrUpdate(Realm realm, hu.szoftarch.hf.gpschat.model.Chat object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (hu.szoftarch.hf.gpschat.model.Chat) cachedRealmObject;
        } else {
            hu.szoftarch.hf.gpschat.model.Chat realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
                long pkColumnIndex = table.getPrimaryKey();
                long rowIndex = table.findFirstLong(pkColumnIndex, ((ChatRealmProxyInterface) object).realmGet$id());
                if (rowIndex != TableOrView.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.ChatRealmProxy();
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

    public static hu.szoftarch.hf.gpschat.model.Chat copy(Realm realm, hu.szoftarch.hf.gpschat.model.Chat newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (hu.szoftarch.hf.gpschat.model.Chat) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            hu.szoftarch.hf.gpschat.model.Chat realmObject = realm.createObjectInternal(hu.szoftarch.hf.gpschat.model.Chat.class, ((ChatRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((ChatRealmProxyInterface) realmObject).realmSet$name(((ChatRealmProxyInterface) newObject).realmGet$name());

            RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) newObject).realmGet$messageList();
            if (messageListList != null) {
                RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListRealmList = ((ChatRealmProxyInterface) realmObject).realmGet$messageList();
                for (int i = 0; i < messageListList.size(); i++) {
                    hu.szoftarch.hf.gpschat.model.Message messageListItem = messageListList.get(i);
                    hu.szoftarch.hf.gpschat.model.Message cachemessageList = (hu.szoftarch.hf.gpschat.model.Message) cache.get(messageListItem);
                    if (cachemessageList != null) {
                        messageListRealmList.add(cachemessageList);
                    } else {
                        messageListRealmList.add(MessageRealmProxy.copyOrUpdate(realm, messageListList.get(i), update, cache));
                    }
                }
            }

            ((ChatRealmProxyInterface) realmObject).realmSet$serverId(((ChatRealmProxyInterface) newObject).realmGet$serverId());
            ((ChatRealmProxyInterface) realmObject).realmSet$type(((ChatRealmProxyInterface) newObject).realmGet$type());
            return realmObject;
        }
    }

    public static long insert(Realm realm, hu.szoftarch.hf.gpschat.model.Chat object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
        long tableNativePtr = table.getNativeTablePointer();
        ChatColumnInfo columnInfo = (ChatColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = TableOrView.NO_MATCH;
        Object primaryKeyValue = ((ChatRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ChatRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(((ChatRealmProxyInterface) object).realmGet$id(), false);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((ChatRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }

        RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) object).realmGet$messageList();
        if (messageListList != null) {
            long messageListNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messageListIndex, rowIndex);
            for (hu.szoftarch.hf.gpschat.model.Message messageListItem : messageListList) {
                Long cacheItemIndexmessageList = cache.get(messageListItem);
                if (cacheItemIndexmessageList == null) {
                    cacheItemIndexmessageList = MessageRealmProxy.insert(realm, messageListItem, cache);
                }
                LinkView.nativeAdd(messageListNativeLinkViewPtr, cacheItemIndexmessageList);
            }
            LinkView.nativeClose(messageListNativeLinkViewPtr);
        }

        String realmGet$serverId = ((ChatRealmProxyInterface)object).realmGet$serverId();
        if (realmGet$serverId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
        }
        String realmGet$type = ((ChatRealmProxyInterface)object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
        long tableNativePtr = table.getNativeTablePointer();
        ChatColumnInfo columnInfo = (ChatColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.szoftarch.hf.gpschat.model.Chat object = null;
        while (objects.hasNext()) {
            object = (hu.szoftarch.hf.gpschat.model.Chat) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = TableOrView.NO_MATCH;
                Object primaryKeyValue = ((ChatRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ChatRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(((ChatRealmProxyInterface) object).realmGet$id(), false);
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$name = ((ChatRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                }

                RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) object).realmGet$messageList();
                if (messageListList != null) {
                    long messageListNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messageListIndex, rowIndex);
                    for (hu.szoftarch.hf.gpschat.model.Message messageListItem : messageListList) {
                        Long cacheItemIndexmessageList = cache.get(messageListItem);
                        if (cacheItemIndexmessageList == null) {
                            cacheItemIndexmessageList = MessageRealmProxy.insert(realm, messageListItem, cache);
                        }
                        LinkView.nativeAdd(messageListNativeLinkViewPtr, cacheItemIndexmessageList);
                    }
                    LinkView.nativeClose(messageListNativeLinkViewPtr);
                }

                String realmGet$serverId = ((ChatRealmProxyInterface)object).realmGet$serverId();
                if (realmGet$serverId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
                }
                String realmGet$type = ((ChatRealmProxyInterface)object).realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, hu.szoftarch.hf.gpschat.model.Chat object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
        long tableNativePtr = table.getNativeTablePointer();
        ChatColumnInfo columnInfo = (ChatColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = TableOrView.NO_MATCH;
        Object primaryKeyValue = ((ChatRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ChatRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == TableOrView.NO_MATCH) {
            rowIndex = table.addEmptyRowWithPrimaryKey(((ChatRealmProxyInterface) object).realmGet$id(), false);
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((ChatRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }

        long messageListNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messageListIndex, rowIndex);
        LinkView.nativeClear(messageListNativeLinkViewPtr);
        RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) object).realmGet$messageList();
        if (messageListList != null) {
            for (hu.szoftarch.hf.gpschat.model.Message messageListItem : messageListList) {
                Long cacheItemIndexmessageList = cache.get(messageListItem);
                if (cacheItemIndexmessageList == null) {
                    cacheItemIndexmessageList = MessageRealmProxy.insertOrUpdate(realm, messageListItem, cache);
                }
                LinkView.nativeAdd(messageListNativeLinkViewPtr, cacheItemIndexmessageList);
            }
        }
        LinkView.nativeClose(messageListNativeLinkViewPtr);

        String realmGet$serverId = ((ChatRealmProxyInterface)object).realmGet$serverId();
        if (realmGet$serverId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.serverIdIndex, rowIndex, false);
        }
        String realmGet$type = ((ChatRealmProxyInterface)object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(hu.szoftarch.hf.gpschat.model.Chat.class);
        long tableNativePtr = table.getNativeTablePointer();
        ChatColumnInfo columnInfo = (ChatColumnInfo) realm.schema.getColumnInfo(hu.szoftarch.hf.gpschat.model.Chat.class);
        long pkColumnIndex = table.getPrimaryKey();
        hu.szoftarch.hf.gpschat.model.Chat object = null;
        while (objects.hasNext()) {
            object = (hu.szoftarch.hf.gpschat.model.Chat) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = TableOrView.NO_MATCH;
                Object primaryKeyValue = ((ChatRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ChatRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == TableOrView.NO_MATCH) {
                    rowIndex = table.addEmptyRowWithPrimaryKey(((ChatRealmProxyInterface) object).realmGet$id(), false);
                }
                cache.put(object, rowIndex);
                String realmGet$name = ((ChatRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
                }

                long messageListNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messageListIndex, rowIndex);
                LinkView.nativeClear(messageListNativeLinkViewPtr);
                RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) object).realmGet$messageList();
                if (messageListList != null) {
                    for (hu.szoftarch.hf.gpschat.model.Message messageListItem : messageListList) {
                        Long cacheItemIndexmessageList = cache.get(messageListItem);
                        if (cacheItemIndexmessageList == null) {
                            cacheItemIndexmessageList = MessageRealmProxy.insertOrUpdate(realm, messageListItem, cache);
                        }
                        LinkView.nativeAdd(messageListNativeLinkViewPtr, cacheItemIndexmessageList);
                    }
                }
                LinkView.nativeClose(messageListNativeLinkViewPtr);

                String realmGet$serverId = ((ChatRealmProxyInterface)object).realmGet$serverId();
                if (realmGet$serverId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.serverIdIndex, rowIndex, realmGet$serverId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.serverIdIndex, rowIndex, false);
                }
                String realmGet$type = ((ChatRealmProxyInterface)object).realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
                }
            }
        }
    }

    public static hu.szoftarch.hf.gpschat.model.Chat createDetachedCopy(hu.szoftarch.hf.gpschat.model.Chat realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        hu.szoftarch.hf.gpschat.model.Chat unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (hu.szoftarch.hf.gpschat.model.Chat)cachedObject.object;
            } else {
                unmanagedObject = (hu.szoftarch.hf.gpschat.model.Chat)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new hu.szoftarch.hf.gpschat.model.Chat();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((ChatRealmProxyInterface) unmanagedObject).realmSet$id(((ChatRealmProxyInterface) realmObject).realmGet$id());
        ((ChatRealmProxyInterface) unmanagedObject).realmSet$name(((ChatRealmProxyInterface) realmObject).realmGet$name());

        // Deep copy of messageList
        if (currentDepth == maxDepth) {
            ((ChatRealmProxyInterface) unmanagedObject).realmSet$messageList(null);
        } else {
            RealmList<hu.szoftarch.hf.gpschat.model.Message> managedmessageListList = ((ChatRealmProxyInterface) realmObject).realmGet$messageList();
            RealmList<hu.szoftarch.hf.gpschat.model.Message> unmanagedmessageListList = new RealmList<hu.szoftarch.hf.gpschat.model.Message>();
            ((ChatRealmProxyInterface) unmanagedObject).realmSet$messageList(unmanagedmessageListList);
            int nextDepth = currentDepth + 1;
            int size = managedmessageListList.size();
            for (int i = 0; i < size; i++) {
                hu.szoftarch.hf.gpschat.model.Message item = MessageRealmProxy.createDetachedCopy(managedmessageListList.get(i), nextDepth, maxDepth, cache);
                unmanagedmessageListList.add(item);
            }
        }
        ((ChatRealmProxyInterface) unmanagedObject).realmSet$serverId(((ChatRealmProxyInterface) realmObject).realmGet$serverId());
        ((ChatRealmProxyInterface) unmanagedObject).realmSet$type(((ChatRealmProxyInterface) realmObject).realmGet$type());
        return unmanagedObject;
    }

    static hu.szoftarch.hf.gpschat.model.Chat update(Realm realm, hu.szoftarch.hf.gpschat.model.Chat realmObject, hu.szoftarch.hf.gpschat.model.Chat newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((ChatRealmProxyInterface) realmObject).realmSet$name(((ChatRealmProxyInterface) newObject).realmGet$name());
        RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListList = ((ChatRealmProxyInterface) newObject).realmGet$messageList();
        RealmList<hu.szoftarch.hf.gpschat.model.Message> messageListRealmList = ((ChatRealmProxyInterface) realmObject).realmGet$messageList();
        messageListRealmList.clear();
        if (messageListList != null) {
            for (int i = 0; i < messageListList.size(); i++) {
                hu.szoftarch.hf.gpschat.model.Message messageListItem = messageListList.get(i);
                hu.szoftarch.hf.gpschat.model.Message cachemessageList = (hu.szoftarch.hf.gpschat.model.Message) cache.get(messageListItem);
                if (cachemessageList != null) {
                    messageListRealmList.add(cachemessageList);
                } else {
                    messageListRealmList.add(MessageRealmProxy.copyOrUpdate(realm, messageListList.get(i), true, cache));
                }
            }
        }
        ((ChatRealmProxyInterface) realmObject).realmSet$serverId(((ChatRealmProxyInterface) newObject).realmGet$serverId());
        ((ChatRealmProxyInterface) realmObject).realmSet$type(((ChatRealmProxyInterface) newObject).realmGet$type());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Chat = [");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messageList:");
        stringBuilder.append("RealmList<Message>[").append(realmGet$messageList().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{serverId:");
        stringBuilder.append(realmGet$serverId() != null ? realmGet$serverId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRealmProxy aChat = (ChatRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aChat.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aChat.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aChat.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}

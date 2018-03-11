/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Chris James
 * @date 2 Mar 2017
 */
public class DefaultObjectRegistry implements
                                   ObjectRegistry {

    private final Map<UUID, Object> mObjectMap;

    public DefaultObjectRegistry(){
        mObjectMap = new HashMap<>();
    }

    @NonNull
    @Override
    public <RegistryT> String put(@NonNull RegistryT object) {
        UUID key = UUID.randomUUID();
        mObjectMap.put(key, object);
        return key.toString();
    }

    @Nullable
    @Override
    public <RegistryT> RegistryT get(@NonNull String registryKey) {
        UUID key = UUID.fromString(registryKey);
        return (RegistryT) mObjectMap.remove(key);
    }
}
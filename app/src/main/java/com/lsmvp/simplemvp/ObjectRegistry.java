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

/**
 * Interface for Object Registry.
 * Important: Do not be tempted to add methods to this interface. "peeking" at an object without removing it would only
 * be required if the object actually belongs to a controlling model.
 * @author Chris James
 * @date 2 Mar 2017
 */
public interface ObjectRegistry {

    /**
     * Place object in registry. Retrieve via {@link ObjectRegistry#get(String)}
     * @return unique key string (handy for placing in bundles)
     */
    @NonNull
    <RegistryT> String put(@NonNull RegistryT object);

    /**
     * Remove object from registry and return it.
     * @param registryKey key returned by {@link ObjectRegistry#put(RegistryT)}
     * @return Object corresponding to key if found, null if not found
     */
    @Nullable
    <RegistryT> RegistryT get(@NonNull String registryKey);
}

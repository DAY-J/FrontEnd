package com.example.dayj.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal val Context.dataFileStore by preferencesDataStore(name = "datafile")
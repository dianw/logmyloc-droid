/*
 * Copyright 2012 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.meruvian.midas.content.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Dias Nurul Arifin
 * @author Dian Aditya
 * 
 */
public class MidasDatabase extends SQLiteOpenHelper {
	public static final String DATABASE = "mutiaraiman.db";
	private static final int VERSION_CODE = 1;
	private static final int VERSION = 1;

	public interface Tables {
	}

	public MidasDatabase(Context context) {
		super(context, DATABASE, null, VERSION_CODE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {

		}
	}

}
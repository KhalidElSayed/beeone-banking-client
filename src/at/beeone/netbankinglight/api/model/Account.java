/*
 * Copyright (C) 2012 BeeOne GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.beeone.netbankinglight.api.model;

import java.util.List;

public interface Account {

	public abstract String getId();

	public abstract String getType();

	public abstract Long getBalance();

	public abstract Long getOverdraft();

	public abstract List<User> getOwners();

	public abstract String getIban();

	public abstract Long getAvailableFunds();

	public abstract AccountSettings getSettings();

	public abstract void setSettings(AccountSettings settings);

}
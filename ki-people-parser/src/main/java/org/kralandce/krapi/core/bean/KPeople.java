package org.kralandce.krapi.core.bean;

import org.kralandce.krapi.core.model.kraland.MKAccount;
import org.kralandce.krapi.core.model.kraland.MKCharacter;
import org.kralandce.krapi.core.model.kraland.MKCity;
import org.kralandce.krapi.core.model.kraland.MKJob;
import org.kralandce.krapi.core.model.kraland.MKSex;

import java.util.Optional;

public interface KPeople {

    public Optional<MKAccount.Identifier> getAccountIdentifier();

    public Optional<MKAccount.Name> getAccountName();

    public Optional<MKAccount.AvatarURL> getAccountAvatarURL();

    public Optional<MKAccount.PermissionLevel> getAccountPermissionLevel();

    public Optional<MKSex.Identifier> getSexIdentifier();

    public Optional<MKJob.Identifier> getJobIdentifier();

    public Optional<MKJob.AccumulatedLevel> getJobAccumulatedLevel();

    public Optional<MKJob.Area> getJobArea();

    public Optional<MKCity.Identifier> getCityAddressIdentifier();

    public Optional<MKCharacter.WealthLevel> getCharacterWealthLevel();
}

package cl.template.integrations.pokemon;

import lombok.Data;
import java.util.List;

@Data
public class Pokemon {
    private int id;
    private String name;
    private int baseExperience;
    private int height;
    private boolean isDefault;
    private int order;
    private int weight;
    private List<Ability> abilities;
    private List<Form> forms;
    private List<GameIndex> gameIndices;
    private List<HeldItem> heldItems;
    private String locationAreaEncounters;
    private List<Move> moves;
    private Species species;
}

@Data
class Ability {
    private boolean isHidden;
    private int slot;
    private NamedResource ability;
}

@Data
class Form {
    private String name;
    private String url;
}

@Data
class GameIndex {
    private int gameIndex;
    private NamedResource version;
}

@Data
class HeldItem {
    private NamedResource item;
    private List<VersionDetail> versionDetails;
}

@Data
class VersionDetail {
    private int rarity;
    private NamedResource version;
}

@Data
class Move {
    private NamedResource move;
    private List<VersionGroupDetail> versionGroupDetails;
}

@Data
class VersionGroupDetail {
    private int levelLearnedAt;
    private NamedResource versionGroup;
    private NamedResource moveLearnMethod;
}

@Data
class Species {
    private String name;
    private String url;
}

@Data
class NamedResource {
    private String name;
    private String url;
}

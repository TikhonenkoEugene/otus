package org.qadev.dataprocessor;

import org.qadev.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}

package com.example.projet_absences_enseignants.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;

import java.util.ArrayList;
import java.util.List;

public class RapportAbsenceAdapter extends RecyclerView.Adapter<RapportAbsenceAdapter.AbsenceViewHolder> {

    private List<Absence> absences = new ArrayList<>();

    @NonNull
    @Override
    public AbsenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rapport_absence, parent, false);
        return new AbsenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceViewHolder holder, int position) {
        Absence absence = absences.get(position);
        holder.bind(absence);
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
        notifyDataSetChanged();
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {

        public AbsenceViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Absence absence) {
            // Lier les données de l'absence avec les vues
            // Par exemple : textViewDate.setText(absence.getDate());
        }
    }
}

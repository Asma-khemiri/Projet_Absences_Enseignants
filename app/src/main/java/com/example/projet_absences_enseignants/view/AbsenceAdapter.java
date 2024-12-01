package com.example.projet_absences_enseignants.view;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;

import java.util.List;

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.AbsenceViewHolder> {

    private List<Absence> absenceList;
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    // Constructeur avec un écouteur de suppression
    public AbsenceAdapter(List<Absence> absenceList, OnItemClickListener listener, OnDeleteClickListener deleteListener) {
        this.absenceList = absenceList;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @Override
    public AbsenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absence, parent, false);
        return new AbsenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbsenceViewHolder holder, int position) {
        Absence absence = absenceList.get(position);

        SpannableString enseignementText = new SpannableString("Enseignement: " + absence.getEnseignement());
        enseignementText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Enseignement: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        enseignementText.setSpan(new ForegroundColorSpan(Color.BLACK), "Enseignement: ".length(), enseignementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvEnseignement.setText(enseignementText);

        SpannableString justificationText = new SpannableString("Justification: " + absence.getStatut());
        justificationText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Justification: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        justificationText.setSpan(new ForegroundColorSpan(Color.BLACK), "Justification: ".length(), justificationText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvJustification.setText(justificationText);

        SpannableString dateText = new SpannableString("Date: " + absence.getDate());
        dateText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Date: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dateText.setSpan(new ForegroundColorSpan(Color.BLACK), "Date: ".length(), dateText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvDate.setText(dateText);

        SpannableString heureText = new SpannableString("Heure: " + absence.getHeure());
        heureText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Heure: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heureText.setSpan(new ForegroundColorSpan(Color.BLACK), "Heure: ".length(), heureText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvHeure.setText(heureText);

        SpannableString classeText = new SpannableString("Classe: " + absence.getClasse());
        classeText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Classe: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        classeText.setSpan(new ForegroundColorSpan(Color.BLACK), "Classe: ".length(), classeText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvClasse.setText(classeText);

        // Lorsque l'on clique sur l'élément, on appelle l'écouteur
        holder.itemView.setOnClickListener(v -> listener.onItemClick(absence));

        // Lorsque l'on clique sur le bouton de suppression, on appelle l'écouteur de suppression
        holder.btnDeleteAbsence.setOnClickListener(v -> deleteListener.onDeleteClick(absence));
    }

    @Override
    public int getItemCount() {
        return absenceList.size();
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnseignement, tvJustification, tvDate, tvHeure, tvClasse;
        Button btnDeleteAbsence;

        public AbsenceViewHolder(View itemView) {
            super(itemView);
            tvEnseignement = itemView.findViewById(R.id.tvEnseignement);
            tvJustification = itemView.findViewById(R.id.tvJustification);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvClasse = itemView.findViewById(R.id.tvClasse);
            btnDeleteAbsence = itemView.findViewById(R.id.btnDeleteAbsence); // Assure-toi que ce bouton existe dans le layout item
        }
    }

    // Interface pour les clics sur les éléments
    public interface OnItemClickListener {
        void onItemClick(Absence absence);
    }

    // Interface pour les clics sur le bouton de suppression
    public interface OnDeleteClickListener {
        void onDeleteClick(Absence absence);
    }
}

package com.example.projet_absences_enseignants.view;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;

import java.util.List;

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.AbsenceViewHolder> {

    private List<Absence> absenceList;
    private OnItemClickListener listener;

    public AbsenceAdapter(List<Absence> absenceList) {
        this.absenceList = absenceList;
        this.listener = listener;
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
        enseignementText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Enseignement: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bleu pour "Enseignement"
        enseignementText.setSpan(new ForegroundColorSpan(Color.BLACK), "Enseignement: ".length(), enseignementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Noir pour la valeur
        holder.tvEnseignement.setText(enseignementText);

        SpannableString justificationText = new SpannableString("Justification: " + absence.getStatut());
        justificationText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Justification: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bleu pour "Justification"
        justificationText.setSpan(new ForegroundColorSpan(Color.BLACK), "Justification: ".length(), justificationText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Noir pour la valeur
        holder.tvJustification.setText(justificationText);

        SpannableString dateText = new SpannableString("Date: " + absence.getDate());
        dateText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Date: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bleu pour "Date"
        dateText.setSpan(new ForegroundColorSpan(Color.BLACK), "Date: ".length(), dateText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Noir pour la valeur
        holder.tvDate.setText(dateText);

        SpannableString heureText = new SpannableString("Heure: " + absence.getHeure());
        heureText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Heure: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bleu pour "Heure"
        heureText.setSpan(new ForegroundColorSpan(Color.BLACK), "Heure: ".length(), heureText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Noir pour la valeur
        holder.tvHeure.setText(heureText);

        SpannableString classeText = new SpannableString("Classe: " + absence.getClasse());
        classeText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Classe: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bleu pour "Classe"
        classeText.setSpan(new ForegroundColorSpan(Color.BLACK), "Classe: ".length(), classeText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Noir pour la valeur
        holder.tvClasse.setText(classeText);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(absence));  // Lors d'un clic sur l'item, déclenche l'action
    }

    @Override
    public int getItemCount() {
        return absenceList.size();
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnseignement, tvJustification, tvDate, tvHeure, tvClasse;

        public AbsenceViewHolder(View itemView) {
            super(itemView);
            tvEnseignement = itemView.findViewById(R.id.tvEnseignement);
            tvJustification = itemView.findViewById(R.id.tvJustification);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvClasse = itemView.findViewById(R.id.tvClasse);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Absence absence);
    }
}

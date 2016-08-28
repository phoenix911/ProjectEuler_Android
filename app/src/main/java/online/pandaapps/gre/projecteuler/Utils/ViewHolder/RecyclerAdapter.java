package online.pandaapps.gre.projecteuler.Utils.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.R;

/**
 * Created by sangeet on 29/08/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    String[] ProblemID,ProblemTitle;

    public RecyclerAdapter(String[] ProblemID,String[] ProblemTitle){

        this.ProblemID = ProblemID;
        this.ProblemTitle = ProblemTitle;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_problem, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tvProblemID.setText(ProblemID[position]);
        holder.tvProblemTitle.setText(ProblemTitle[position]);
    }

    @Override
    public int getItemCount() {
        return ProblemID.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView tvProblemID,tvProblemTitle;


        public RecyclerViewHolder(View view) {
            super(view);

            tvProblemID = (TextView) view.findViewById(R.id.problemId);
            tvProblemTitle = (TextView) view.findViewById(R.id.problemTitle);

        }
    }


}

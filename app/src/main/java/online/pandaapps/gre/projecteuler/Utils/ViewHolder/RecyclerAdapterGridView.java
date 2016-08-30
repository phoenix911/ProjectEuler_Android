package online.pandaapps.gre.projecteuler.Utils.ViewHolder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import online.pandaapps.gre.projecteuler.Constants;
import online.pandaapps.gre.projecteuler.Euler.Recent;
import online.pandaapps.gre.projecteuler.R;

/**
 * Created by sangeet on 30/08/16.
 */
public class RecyclerAdapterGridView extends RecyclerView.Adapter<RecyclerAdapterGridView.RecyclerViewHolderGV> {

    String[] ProblemList;
    static int nAndD;
    int num = 1;
    int diff = 2;

    public RecyclerAdapterGridView(String[] ProblemList,int nAndD){
        this.ProblemList = ProblemList;
        this.nAndD = nAndD;
    }


    @Override
    public RecyclerViewHolderGV onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_problem_number, parent, false);
        RecyclerViewHolderGV recyclerViewHolderGV = new RecyclerViewHolderGV(view);
        return recyclerViewHolderGV;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderGV holder, int position) {

        if (nAndD == num){
            holder.tvProblemGroup.setText(ProblemList[position] + "+ ");
            holder.cv.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.colorFAB3));
        }else if (nAndD == diff){
            holder.tvProblemGroup.setText(ProblemList[position]+"");
            holder.cv.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.colorFAB1));

        }

    }



    @Override
    public int getItemCount() {
        return ProblemList.length;
    }


    public static class RecyclerViewHolderGV extends RecyclerView.ViewHolder{

        TextView tvProblemGroup;
        CardView cv;

        public RecyclerViewHolderGV(View view) {
            super(view);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do the thing
                    Context context = v.getContext();
                    Intent statRecent = new Intent(context, Recent.class);


                    switch (nAndD){
                        case 1:
                            String no = tvProblemGroup.getText().toString().replaceAll("\\+","");
                            statRecent.putExtra(Constants.RecentFlagTitle,"2");
                            statRecent.putExtra("first_problem",no);

                            break;
                        case 2:
                            String difficulty = tvProblemGroup.getText().toString().replaceAll("\\+","");
                            statRecent.putExtra(Constants.RecentFlagTitle,"3");
                            statRecent.putExtra("first_problem",difficulty);
                            break;
                    }
                    context.startActivity(statRecent);




                }
            });

            tvProblemGroup = (TextView) view.findViewById(R.id.questionBulk);
            cv = (CardView) view.findViewById(R.id.cvIndiNo);


        }


    }
}

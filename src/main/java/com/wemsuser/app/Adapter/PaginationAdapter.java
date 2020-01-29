package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wemsuser.app.Activity.Testpagination;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.ServiceDatum;
import com.wemsuser.app.Response.ServiceProviderDatum;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private PaginationAdapterCallback mCallback;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private Context context;
    private ArrayList<ServiceProviderDatum> ServiceList;
    private ArrayList<ServiceProviderDatum>fullList;

    public PaginationAdapter(Context serviceList, ArrayList<ServiceProviderDatum> serviceProvider) {
        this.context = serviceList;
        this.ServiceList = serviceProvider;
        this.mCallback = (PaginationAdapterCallback) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.homexml, parent, false);
                viewHolder = new ServiceList(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.itemloading, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                final ServiceList movieVH = (ServiceList) viewHolder;

                movieVH.ShopTitle.setText(ServiceList.get(position).getUserName());
                movieVH.Shop_Address.setText(ServiceList.get(position).getUserAddress());
                movieVH.Shop_phone.setText(ServiceList.get(position).getUserPhone());
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) viewHolder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

//                    loadingVH.mErrorTxt.setText(
//                            errorMsg != null ?
//                                    errorMsg :"An Error occoured";

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

        @Override
        public int getItemCount () {
            return ServiceList == null ? 0 : ServiceList.size();
        }

    @Override
    public int getItemViewType(int position) {
        return (position == ServiceList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /**
         * Displays Pagination retry footer view along with appropriate errorMsg
         *
         * @param show
         * @param errorMsg to display if page load fails
         */
        public void showRetry ( boolean show, @Nullable String errorMsg){
            retryPageLoad = show;
            notifyItemChanged(ServiceList.size() - 1);

            if (errorMsg != null) this.errorMsg = errorMsg;
        }


        public void add (ServiceProviderDatum datum){
            fullList.add(datum);
            notifyItemInserted(ServiceList.size() - 1);
        }

        public void addAll (ArrayList <ServiceProviderDatum> moveResults) {
            for (ServiceProviderDatum result : moveResults) {
                add(result);
            }
        }

        public void remove (ServiceProviderDatum r){
            int position = ServiceList.indexOf(r);
            if (position > -1) {
                ServiceList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void clear () {
            isLoadingAdded = false;
            while (getItemCount() > 0) {
                remove(getItem(0));
            }
        }

        public boolean isEmpty () {
            return getItemCount() == 0;
        }


        public void addLoadingFooter () {
            isLoadingAdded = true;
            add(new ServiceProviderDatum());
        }

        public void removeLoadingFooter () {
            isLoadingAdded = false;

            int position = ServiceList.size() - 1;
            ServiceProviderDatum result = getItem(position);

            if (result != null) {
                ServiceList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public ServiceProviderDatum getItem ( int position){
            return ServiceList.get(position);
        }




    protected class ServiceList extends RecyclerView.ViewHolder {
        private TextView ShopTitle;
        private TextView Shop_Address;
        private TextView Shop_phone; // displays "year | language"
        private ImageView Service_image;
        private ProgressBar mProgress;

        public ServiceList(View itemView) {
            super(itemView);

            ShopTitle = itemView.findViewById(R.id.Text1);
            Shop_Address = itemView.findViewById(R.id.Text2);
            Shop_phone = itemView.findViewById(R.id.Text_mobileNo);
            Service_image = itemView.findViewById(R.id.Image_home);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }
}


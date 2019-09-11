package com.phonepe.testapp;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chethan on 2019-09-11.
 */
public class MainFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        initViews();
    }

    private void initViews() {

        ViewModelProvider.AndroidViewModelFactory viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());



        recyclerView.setAdapter(new TestAdapter(this, ViewModelProviders.of(this)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LifecycleOwner lifecycleOwner;
        ViewModelProvider viewModelProvider;

        public TestAdapter(LifecycleOwner lifecycleOwner, ViewModelProvider viewModelProvider) {
            this.lifecycleOwner = lifecycleOwner;
            this.viewModelProvider = viewModelProvider;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new TestViewHolderTypeOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_one, parent, false),
                        this.lifecycleOwner);
            }
            return new TestViewHolderTypeTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_two, parent, false),
                    this.lifecycleOwner);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == 1) {
                WidgetOneVM widgetOneVM = viewModelProvider.get(String.valueOf(position) , WidgetOneVM.class);
                ((TestViewHolderTypeOne) holder).setVM(widgetOneVM);
            } else {
                WidgetTwoVM widgetTwoVM = viewModelProvider.get(String.valueOf(position) , WidgetTwoVM.class);
                ((TestViewHolderTypeTwo) holder).setVM(widgetTwoVM);
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return 1;
            }
            return 2;
        }
    }


    class TestViewHolderTypeOne extends RecyclerView.ViewHolder implements LifecycleObserver {

        private ViewModel vm;

        public TestViewHolderTypeOne(@NonNull View itemView, LifecycleOwner lifecycleOwner) {
            super(itemView);
            lifecycleOwner.getLifecycle().addObserver(this);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void onCreate() {
            Log.d( "zzzzzz","TestViewHolderTypeOne ON_CREATE");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        private void onPause() {
            Log.d( "zzzzzz","TestViewHolderTypeOne ON_PAUSE");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            Log.d( "zzzzzz","TestViewHolderTypeOne ON_RESUME");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        private void onDestroy() {
            Log.d( "zzzzzz","TestViewHolderTypeOne ON_DESTROY");
        }

        private void setVM(ViewModel vm) {
            this.vm = vm;
        }

    }

    class TestViewHolderTypeTwo extends RecyclerView.ViewHolder implements LifecycleObserver {

        private ViewModel vm;

        public TestViewHolderTypeTwo(@NonNull View itemView, LifecycleOwner lifecycleOwner) {
            super(itemView);
            lifecycleOwner.getLifecycle().addObserver(this);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void onCreate() {
            Log.d( "zzzzzz","TestViewHolderTypeTwo ON_CREATE");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        private void onPause() {
            Log.d( "zzzzzz","TestViewHolderTypeTwo ON_PAUSE");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            Log.d( "zzzzzz","TestViewHolderTypeTwo ON_RESUME");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        private void onDestroy() {
            Log.d( "zzzzzz","TestViewHolderTypeTwo ON_DESTROY");
        }



        private void setVM(ViewModel vm) {
            this.vm = vm;
        }
    }

    public static class WidgetOneVM extends ViewModel {

        public WidgetOneVM() {
        }
    }

    public static class WidgetTwoVM extends ViewModel {

        public WidgetTwoVM() {
        }
    }

}

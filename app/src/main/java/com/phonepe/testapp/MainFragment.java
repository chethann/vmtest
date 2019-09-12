package com.phonepe.testapp;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;

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
                ((TestViewHolderTypeOne) holder).removeExistingData();
                ((TestViewHolderTypeOne) holder).setVM(widgetOneVM, position);
            } else {
                WidgetTwoVM widgetTwoVM = viewModelProvider.get(String.valueOf(position) , WidgetTwoVM.class);
                ((TestViewHolderTypeTwo) holder).removeExistingData();
                ((TestViewHolderTypeTwo) holder).setVM(widgetTwoVM, position);
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return 1;
            }
            return 2;
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            if (holder instanceof TestViewHolderTypeOne) {
                ((TestViewHolderTypeOne) holder).stopObserving();
            } else if (holder instanceof  TestViewHolderTypeTwo) {
                ((TestViewHolderTypeTwo) holder).stopObserving();
            }
        }
    }


    class TestViewHolderTypeOne extends RecyclerView.ViewHolder implements LifecycleObserver {

        private WidgetOneVM vm;
        private TextView textView;
        private Observer<String> observer;

        private LifecycleOwner lifecycleOwner;
        private int pos;

        public TestViewHolderTypeOne(@NonNull View itemView, LifecycleOwner lifecycleOwner) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            lifecycleOwner.getLifecycle().addObserver(this);
            this.lifecycleOwner = lifecycleOwner;
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

        public void removeExistingData() {
            textView.setText("Waiting for data from VM...");
        }

        public void setVM(WidgetOneVM vm, final int position) {
            this.pos = position;
            this.vm = vm;
            this.vm.getData();

            observer = new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Log.d( "zzzzzz","Data changed at " + position);
                    textView.setText(s + " at pos " + position);
                }
            };

            this.vm.observeData().observe(lifecycleOwner, observer);
        }

        public void stopObserving() {
            if (this.observer != null) {
                this.vm.data.removeObserver(this.observer);
                Log.d( "zzzzzz","Removed observer at " + pos);
            }
        }

    }

    class TestViewHolderTypeTwo extends RecyclerView.ViewHolder implements LifecycleObserver {

        private WidgetTwoVM vm;
        private LifecycleOwner lifecycleOwner;
        private TextView textView;
        private Observer<String> observer;
        private int pos;

        public TestViewHolderTypeTwo(@NonNull View itemView, LifecycleOwner lifecycleOwner) {
            super(itemView);
            lifecycleOwner.getLifecycle().addObserver(this);
            textView = itemView.findViewById(R.id.text);
            this.lifecycleOwner = lifecycleOwner;
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

        public void removeExistingData() {
            textView.setText("Waiting for data from VM...");
        }

        public void setVM(WidgetTwoVM vm, final int position) {
            this.vm = vm;
            this.vm.getData();
            pos = position;

            observer = new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Log.d( "zzzzzz","Data changed at " + position);
                    textView.setText(s + " at pos " + position);
                }
            };

            this.vm.observeData().observe(lifecycleOwner, observer);
        }

        public void stopObserving() {
            if (this.observer != null) {
                this.vm.data.removeObserver(this.observer);
                Log.d( "zzzzzz","Removed observer at " + pos);
            }
        }
    }

    public static class WidgetOneVM extends ViewModel {

        // todo publish values to observer once view is recycled and check the behaviour

        private MutableLiveData<String> data = new MutableLiveData();
        private String uuid;

        public WidgetOneVM() {
        }

        public void getData() {
            if (TextUtils.isEmpty(uuid)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uuid = UUID.randomUUID().toString();
                        data.postValue("From VM One " + uuid);
                    }
                }, 1000);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.postValue("From VM One Memory " + uuid);
                    }
                }, 20000);
            }
        }

        public LiveData<String> observeData() {
            return data;
        }
    }

    public static class WidgetTwoVM extends ViewModel {

        private MutableLiveData<String> data = new MutableLiveData();
        private String uuid;

        public WidgetTwoVM() {
        }

        public void getData() {
            if (TextUtils.isEmpty(uuid)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uuid = UUID.randomUUID().toString();
                        data.postValue("From VM Two " + uuid);
                    }
                }, 1000);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.postValue("From VM Two Memory " + uuid);
                    }
                }, 20000);
            }
        }

        public LiveData<String> observeData() {
            return data;
        }
    }

}

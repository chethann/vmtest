package com.phonepe.testapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;

/**
 * Created by Chethan on 2019-09-11.
 */
public class RVTestFragment extends Fragment {
    static int idCounter = 0;
    RecyclerView recyclerview;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.recycler_view);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rv, container, false);
    }

    private void initView() {
        recyclerview.setAdapter(new TestAdapter());
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_rv, parent, false);
        TestViewHolder testViewHolder = new TestViewHolder(view);

//            FrameLayout frameLayout = new FrameLayout(getContext());
        int id = idCounter++;
//            frameLayout.setId(id);
//            testViewHolder.layout.addView(frameLayout);
        // testViewHolder.frameLayout.setId(id);

        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TestViewHolder holder, final int position) {
        Log.d("zzzzzz", "onBindViewHolder pos " + position);

        int id = UUID.randomUUID().hashCode();

        holder.frameLayout.setId(id);

        int actualId = holder.frameLayout.getId();

        if (id != holder.frameLayout.getId()) {
            Log.d("zzzzzz", "id mismatch for pos " + position + " expected " + id + " actual id " + actualId);
        }

//        if (holder.layout.findViewById(holder.frameLayout.getId()) != null) {
//            RVTestFragment.this.getActivity().getSupportFragmentManager().beginTransaction().replace(holder.frameLayout.getId(), RecyclerViewItemFragment.getInstance()).commit();
//        }

        holder.frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (holder.layout.findViewById(holder.frameLayout.getId()) != null) {
                    RVTestFragment.this.getActivity().getSupportFragmentManager().beginTransaction().replace(holder.frameLayout.getId(), RecyclerViewItemFragment.getInstance(position)).commit();
                }
            }
        }, 300);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull TestViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Fragment fragment = RVTestFragment.this.getActivity().getSupportFragmentManager().findFragmentById(holder.frameLayout.getId());
        if (fragment != null) {
            Log.d("zzzzzz", "fragment not null");
            RVTestFragment.this.getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}

class TestViewHolder extends RecyclerView.ViewHolder {

    LinearLayout layout;
    FrameLayout frameLayout;

    public TestViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.layout);
        frameLayout = itemView.findViewById(R.id.framelayout);
    }
}

public static class RecyclerViewItemFragment extends Fragment {

    int position;

    public static RecyclerViewItemFragment getInstance(int position) {
        RecyclerViewItemFragment recyclerViewItemFragment = new RecyclerViewItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        recyclerViewItemFragment.setArguments(bundle);
        return recyclerViewItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        position = getArguments().getInt("position");
        Log.d("zzzzzz", "onCreate " + position);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("zzzzzz", "onViewCreated " + position);
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textview);
        textView.setText("Sample text inside fragment " + position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("zzzzzz", "onCreateView " + position);
        return inflater.inflate(R.layout.fragment_rv_item, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("zzzzzz", "onDestroyView " + position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zzzzzz", "onDestroy " + position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("zzzzzz", "onAttach " + position);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("zzzzzz", "onStart " + position);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("zzzzzz", "onStop " + position);
    }
}
}

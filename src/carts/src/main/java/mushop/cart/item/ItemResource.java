package mushop.cart.item;

import java.util.function.Supplier;

import mushop.cart.cart.Resource;
import mushop.cart.entities.Item;

public class ItemResource implements Resource<Item> {
    private final ItemDAO itemRepository;
    private final Supplier<Item> item;

    public ItemResource(ItemDAO itemRepository, Supplier<Item> item) {
        this.itemRepository = itemRepository;
        this.item = item;
    }

    @Override
    public Runnable destroy() {
        return () -> itemRepository.destroy(value().get());
    }

    @Override
    public Supplier<Item> create() {
        return () -> itemRepository.save(item.get());
    }

    @Override
    public Supplier<Item> value() {
        return item;    // Basically a null method. Gets the item from the supplier.
    }

    @Override
    public Runnable merge(Item toMerge) {
        return () -> itemRepository.save(new Item(value().get(), toMerge.quantity()));
    }
}

#include <cassert>
#include <cstddef>
#include <concepts>
#include <iostream>
#include <new>
#include <utility>

/*
Реализуйте CellarSteward — базовый класс в стиле std::ranges::view_interface).

Наследник может (а может и нет) предоставлять низкоуровневые методы:

    void* pour_bytes(std::size_t);
    std::size_t headroom() const;
    std::size_t vault_size() const;
    std::size_t dregs_size() const;
    bool at_brim() const;

CellarSteward должен доопределять методы:

    decant<T>(args...)  // если есть pour_bytes
    vacancy()           // если есть headroom, иначе vault_size + dregs_size, иначе не поддерживаем
    is_brimming()       // если есть at_brim или vacancy(), иначе не поддерживаем
    load_ratio()        // если есть vault_size + dregs_size, иначе не поддерживаем

Не поддерживаем, значит, что метод не должен быть доступен:
    - ожидается ошибка компиляции при попытке заиспользовать
    - можно ошибку на этапе исполнения за ~5 штрафных баллов
СИНТАКСИС НАСЛЕДОВАНИЯ МОЖНО ИЗМЕНИТЬ (ЧУТЬЧУТЬ)
*/

template <typename T>
concept has_pour_bytes = requires(T& t, std::size_t n) {
    { t.pour_bytes(n) } -> std::convertible_to<void*>;
};

template <typename T>
concept has_headroom = requires(const T& t) {
    { t.headroom() } -> std::convertible_to<std::size_t>;
};

template <typename T>
concept has_vault_size = requires(const T& t) {
    { t.vault_size() } -> std::convertible_to<std::size_t>;
};

template <typename T>
concept has_dregs_size = requires(const T& t) {
    { t.dregs_size() } -> std::convertible_to<std::size_t>;
};

template <typename T>
concept has_at_brim = requires(const T& t) {
    { t.at_brim() } -> std::convertible_to<bool>;
};

template <typename T>
concept can_vacancy = has_headroom<T> || (has_vault_size<T> && has_dregs_size<T>);

template <typename Derived>
struct CellarSteward {
    template <typename T, typename... Args, typename D = Derived>
        requires has_pour_bytes<D>
    T* decant(Args&&... args) {
        void* p = static_cast<D&>(*this).pour_bytes(sizeof(T));
        if (!p) return nullptr;
        return ::new (p) T(std::forward<Args>(args)...);
    }

    template <typename D = Derived>
        requires has_headroom<D>
    std::size_t vacancy() const {
        return static_cast<const D&>(*this).headroom();
    }

    template <typename D = Derived>
        requires (!has_headroom<D> && has_vault_size<D> && has_dregs_size<D>)
    std::size_t vacancy() const {
        const D& d = static_cast<const D&>(*this);
        return d.vault_size() - d.dregs_size();
    }

    template <typename D = Derived>
        requires has_at_brim<D>
    bool is_brimming() const {
        return static_cast<const D&>(*this).at_brim();
    }

    template <typename D = Derived>
        requires (!has_at_brim<D> && can_vacancy<D>)
    bool is_brimming() const {
        return vacancy() == 0;
    }

    template <typename D = Derived>
        requires (has_vault_size<D> && has_dregs_size<D>)
    double load_ratio() const {
        const D& d = static_cast<const D&>(*this);
        return static_cast<double>(d.dregs_size()) / static_cast<double>(d.vault_size());
    }
};

// свой аллокатор
struct BasicCellar : CellarSteward<BasicCellar> {
    explicit BasicCellar(std::size_t cap) : cap(cap), data(new char[cap]) {}
    ~BasicCellar() { delete[] data; }

    void* pour_bytes(std::size_t n) {
        if (used + n > cap) return nullptr;
        void* p = data + used;
        used += n;
        return p;
    }

    std::size_t vault_size() const { return cap; }
    std::size_t dregs_size() const { return used; }

    std::size_t cap;
    std::size_t used = 0;
    char* data;
};

// тоже аллокатор, но size как-то по-другому считаем
struct VacancyCellar : CellarSteward<VacancyCellar> {
    explicit VacancyCellar(std::size_t cap) : cap(cap), data(new char[cap]) {}
    ~VacancyCellar() { delete[] data; }

    void* pour_bytes(std::size_t n) {
        if (used + n > cap) return nullptr;
        void* p = data + used;
        used += n;
        return p;
    }

    std::size_t headroom() const { return cap - used; }
    bool at_brim() const { return used == cap; }

    std::size_t cap;
    std::size_t used = 0;
    char* data;
};

struct WaifCellar : CellarSteward<WaifCellar> {
    explicit WaifCellar(std::size_t cap) : cap(cap), data(new char[cap]) {}
    ~WaifCellar() { delete[] data; }

    void* pour_bytes(std::size_t n) {
        if (used + n > cap) return nullptr;
        void* p = data + used;
        used += n;
        return p;
    }

    std::size_t headroom() const { return cap - used; }

    std::size_t cap;
    std::size_t used = 0;
    char* data;
};

struct BrimCellar : CellarSteward<BrimCellar> {
    static constexpr std::size_t cap = 10;

    void* pour_bytes(std::size_t n) {
        if (used + n > cap) return nullptr;
        void* p = data + used;
        used += n;
        return p;
    }

    bool at_brim() const { return used == cap; }

    std::size_t used = 0;
    char data[cap]{};
};

int main() {
    BasicCellar bc{32};

    assert(bc.vacancy() == 32);
    assert(!bc.is_brimming());
    assert(bc.load_ratio() == 0.0);

    for (int i = 0; i < 8; ++i) {
        auto* p = bc.decant<int>(i);
        assert(p && *p == i);
    }

    assert(bc.vacancy() == 0);
    assert(bc.is_brimming());
    assert(bc.load_ratio() == 1.0);
    assert(bc.decant<int>(42) == nullptr);

    VacancyCellar vc{8};

    assert(vc.vacancy() == 8);
    assert(!vc.is_brimming());

    assert(*vc.decant<int>(1) == 1);
    assert(vc.vacancy() == 4);

    assert(*vc.decant<int>(2) == 2);
    assert(vc.vacancy() == 0);
    assert(vc.is_brimming());

    WaifCellar wc{16};

    assert(wc.vacancy() == 16);
    assert(!wc.is_brimming());

    assert(*wc.decant<double>(1.5) == 1.5);
    assert(*wc.decant<double>(2.5) == 2.5);

    assert(wc.vacancy() == 0);
    assert(wc.is_brimming());
    assert(wc.decant<char>('x') == nullptr);

    BrimCellar br;

    assert(!br.is_brimming());

    std::size_t n = 0;
    while (br.decant<char>('a')) {
        ++n;
    }

    assert(n == BrimCellar::cap);
    assert(br.is_brimming());

    std::cout << "OK\n";
}
